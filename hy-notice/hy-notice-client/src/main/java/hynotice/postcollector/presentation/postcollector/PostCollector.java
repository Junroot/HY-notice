package hynotice.postcollector.presentation.postcollector;

import hynotice.core.domain.Post;
import java.time.LocalDate;
import java.util.List;

public interface PostCollector {

    List<Post> collectNewPosts(LocalDate fromDate);
}
