package postcollector.presentation.controller;

import core.domain.Post;
import core.domain.repository.PostRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import postcollector.domain.CollectingMetaData;
import postcollector.domain.repository.CollectingMetaDataRepository;
import postcollector.presentation.postcollector.PostCollector;
import postcollector.presentation.postcollector.PostCollectors;

@Component
public class PostCollectorController {

    private static final Supplier<LocalDate> DEFAULT_COLLECTING_DATE = () -> LocalDate.now()
        .minusMonths(2);

    private final PostCollectors postCollectors;
    private final CollectingMetaDataRepository collectingMetaDataRepository;
    private final PostRepository postRepository;

    public PostCollectorController(final List<PostCollector> postCollectors,
                                   final CollectingMetaDataRepository collectingMetaDataRepository,
                                   final PostRepository postRepository) {
        this.postCollectors = new PostCollectors(postCollectors);
        this.collectingMetaDataRepository = collectingMetaDataRepository;
        this.postRepository = postRepository;
    }

    @Scheduled(cron = "0 0/30 * * * ?")
    @Transactional
    public void collectAndSavePosts() {
        try {
            System.out.println("[커맨드라인러너 시작]");
            List<Post> newPosts = collectNewPosts();

            for (Post post : newPosts) {
                System.out.printf("%s\t%s\t%s%n", post.getTitle(), post.getUrl(),
                    post.getWritingDate());
            }
            postRepository.saveAll(newPosts);
            collectingMetaDataRepository.save(new CollectingMetaData((long) newPosts.size()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("[커맨드라인러너 끝]");
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
