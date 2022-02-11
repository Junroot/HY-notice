package hynotice.api.presentation.controller;

import hynotice.core.domain.Post;
import hynotice.core.domain.repository.PostRepository;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import hynotice.api.presentation.dto.PostRssView;

@RestController
public class RssFeedController {

    private static final Logger LOGGER = LogManager.getLogger(RssFeedController.class);
    private final PostRepository postRepository;

    public RssFeedController(final PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping(value = "/")
    public PostRssView getPosts() {
        LOGGER.error("테스트");
        List<Post> posts = postRepository.findAll();
        return new PostRssView(posts);
    }
}
