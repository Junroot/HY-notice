package postcollector.presentation.controller;

import java.util.List;
import postcollector.domain.Post;

public interface PostCollector {
    List<Post> collectNewPosts();
}
