package postcollector.presentation.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import postcollector.domain.Post;
import postcollector.domain.PostRepository;

@Component
public class PostCollectorController {

    private final PostRepository postRepository;

    public PostCollectorController(final PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public void collectAndSavePosts() {
        try {
            PostCollectorScanner postCollectorScanner = new PostCollectorScanner();
            List<Post> newPosts = postCollectorScanner.collectNewPosts(LocalDate.of(2022,1,10));

            for (Post post : newPosts) {
                System.out.printf("%s\t%s\t%s%n", post.getTitle(), post.getUrl(), post.getWritingDate());
            }
            postRepository.saveAll(newPosts);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
