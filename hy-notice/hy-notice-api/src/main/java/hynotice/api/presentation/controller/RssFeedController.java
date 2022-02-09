package hynotice.api.presentation.controller;

import hynotice.core.domain.Post;
import hynotice.core.domain.repository.PostRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import hynotice.api.presentation.dto.PostRssView;

@RestController
public class RssFeedController {

    private final PostRepository postRepository;

    public RssFeedController(final PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping(value = "/")
    public PostRssView getPosts() {
        List<Post> posts = postRepository.findAll();
        return new PostRssView(posts);
    }
}
