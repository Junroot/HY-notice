package hynotice.api.presentation.controller;

import hynotice.api.presentation.dto.PostRssView;
import hynotice.api.presentation.service.PostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RssFeedController {

    private final PostService postService;

    public RssFeedController(final PostService postService) {
        this.postService = postService;
    }

    @GetMapping(value = "/rss", produces = "text/xml; charset=utf-8")
    public PostRssView getPosts() {
        return new PostRssView(postService.findRecentPosts());
    }
}
