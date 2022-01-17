package postcollector.presentation.postcollector;

import java.time.LocalDate;
import java.util.List;
import postcollector.domain.Postable;
import postcollector.domain.Post;

public interface PostCollector<T extends Postable> {

    List<Post> collectNewPosts(LocalDate fromDate);
}
