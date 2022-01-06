package postcollector.presentation.controller;

import java.time.LocalDate;
import java.util.List;
import postcollector.domain.ConvertableToPost;
import postcollector.domain.Post;

public interface PostCollector<T extends ConvertableToPost> {

    List<Post> collectNewPosts(LocalDate fromDate);
}
