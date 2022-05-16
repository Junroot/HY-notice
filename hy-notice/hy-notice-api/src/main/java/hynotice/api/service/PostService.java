package hynotice.api.service;

import hynotice.api.presentation.dto.PostResponse;
import hynotice.core.domain.Post;
import hynotice.core.domain.repository.PostRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private static final Pageable RECENT_POSTS_PAGE = PageRequest.of(0, 50,
        Sort.by("writingDate").descending());
    private final PostRepository postRepository;

    public PostService(final PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findRecentPosts() {
        return postRepository.findAll(RECENT_POSTS_PAGE)
            .getContent();
    }

    public List<PostResponse> getPostsByKeywords(final List<String> keywords) {
        List<Post> posts = postRepository.findAllByAnyKeyword(keywords);

        return posts.stream()
            .map(post -> PostResponse.from(post, filterContainingKeywords(post, keywords)))
            .collect(Collectors.toList());
    }

    private List<String> filterContainingKeywords(final Post post, final List<String> keywords) {
        return keywords.stream()
            .filter(post::containsInTitle)
            .collect(Collectors.toList());
    }
}
