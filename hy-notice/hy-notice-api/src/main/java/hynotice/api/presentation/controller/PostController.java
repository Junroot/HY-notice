package hynotice.api.presentation.controller;

import hynotice.api.presentation.dto.PostResponse;
import hynotice.api.service.PostService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/posts")
@RestController
public class PostController {

    private final PostService postService;

    public PostController(final PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getPostsByKeywords(
        @RequestParam(required = false, defaultValue = "") List<String> keywords,
        @RequestParam(required = false, defaultValue = "1") int page) {
        return ResponseEntity.ok(postService.getPostsByKeywords(keywords, page));
    }
}
