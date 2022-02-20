package hynotice.postcollector.presentation.controller;

import hynotice.core.configuration.ExceptionTracer;
import hynotice.core.domain.Post;
import hynotice.core.domain.repository.PostRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import hynotice.postcollector.domain.CollectingMetaData;
import hynotice.postcollector.domain.repository.CollectingMetaDataRepository;
import hynotice.postcollector.presentation.postcollector.PostCollector;
import hynotice.postcollector.presentation.postcollector.PostCollectors;

@Component
public class PostCollectorController {

    private static final Logger LOGGER = LogManager.getLogger(PostCollectorController.class);
    private static final Supplier<LocalDate> DEFAULT_COLLECTING_DATE = () -> LocalDate.now()
        .minusMonths(2);

    private final PostCollectors postCollectors;
    private final CollectingMetaDataRepository collectingMetaDataRepository;
    private final PostRepository postRepository;
    private final ExceptionTracer exceptionTracer;

    public PostCollectorController(
        final List<PostCollector> postCollectors,
        final CollectingMetaDataRepository collectingMetaDataRepository,
        final PostRepository postRepository, final ExceptionTracer exceptionTracer) {
        this.postCollectors = new PostCollectors(postCollectors);
        this.collectingMetaDataRepository = collectingMetaDataRepository;
        this.postRepository = postRepository;
        this.exceptionTracer = exceptionTracer;
    }

    @Scheduled(cron = "0 0/30 * * * ?")
    @Transactional
    public void collectAndSavePosts() {
        try {
            LOGGER.info("[커맨드라인러너 시작]");
            List<Post> newPosts = collectNewPosts();

            for (Post post : newPosts) {
                LOGGER.info(String.format("%s\t%s\t%s%n", post.getTitle(), post.getUrl(),
                    post.getWritingDate()));
            }
            postRepository.saveAll(newPosts);
            collectingMetaDataRepository.save(new CollectingMetaData((long) newPosts.size()));
        } catch (Exception e) {
            LOGGER.error(exceptionTracer.getStackTrace(e));
        } finally {
            LOGGER.info("[커맨드라인러너 끝]");
        }
    }

    private List<Post> collectNewPosts() {
        LocalDate latestCollectingDate = getLatestCollectingDate();
        List<Post> newPosts = postCollectors.collectNewPosts(latestCollectingDate);
        List<Post> oldPosts = postRepository.findAllByWritingDateGreaterThanEqual(
            latestCollectingDate);

        return newPosts.stream()
            .filter(newPost -> !oldPosts.contains(newPost))
            .collect(Collectors.toList());
    }

    private LocalDate getLatestCollectingDate() {
        return collectingMetaDataRepository.findFirstByOrderByCollectingTimeDesc()
            .map(metaData -> metaData.getCollectingTime().toLocalDate())
            .orElseGet(DEFAULT_COLLECTING_DATE);
    }
}
