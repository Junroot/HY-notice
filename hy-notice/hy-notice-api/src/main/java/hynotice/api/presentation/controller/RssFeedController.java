package hynotice.api.presentation.controller;

import hynotice.api.presentation.dto.PostRssView;
import hynotice.core.domain.Post;
import hynotice.core.domain.repository.PostRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RssFeedController {

    private final PostRepository postRepository;

    public RssFeedController(final PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping(value = "/rss", produces = "text/xml; charset=utf-8")
    public PostRssView getPosts() {
        List<Post> posts = postRepository.findAll();
        return new PostRssView(posts);
    }
}
