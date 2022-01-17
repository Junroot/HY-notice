package postcollector.presentation.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import postcollector.domain.CollectingMetaData;
import postcollector.domain.Post;
import postcollector.domain.repository.CollectingMetaDataRepository;
import postcollector.domain.repository.PostRepository;
import postcollector.presentation.postcollector.PostCollector;
import postcollector.presentation.postcollector.PostCollectors;

@Component
public class PostCollectorController {

    private static final Supplier<LocalDate> DEFAULT_COLLECTING_DATE = () -> LocalDate.now()
        .minusMonths(2);

    private final PostCollectors postCollectors;
    private final CollectingMetaDataRepository collectingMetaDataRepository;
    private final PostRepository postRepository;

    public PostCollectorController(final List<PostCollector<?>> postCollectors,
                                   final CollectingMetaDataRepository collectingMetaDataRepository,
                                   final PostRepository postRepository) {
        this.postCollectors = new PostCollectors(postCollectors);
        this.collectingMetaDataRepository = collectingMetaDataRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    public void collectAndSavePosts() {
        try {
            List<Post> newPosts = collectNewPosts();

            for (Post post : newPosts) {
                System.out.printf("%s\t%s\t%s%n", post.getTitle(), post.getUrl(),
                    post.getWritingDate());
            }
            postRepository.saveAll(newPosts);
            collectingMetaDataRepository.save(new CollectingMetaData((long) newPosts.size()));

        } catch (Exception e) {
            e.printStackTrace();
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
