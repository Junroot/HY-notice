package hynotice.postcollector.presentation.postcollector;

import hynotice.core.domain.Post;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PostCollectors {

    private final List<PostCollector> postCollectors;

    public PostCollectors(final List<PostCollector> postCollectors) {
        this.postCollectors = new ArrayList<>(postCollectors);
    }

    public List<Post> collectNewPosts(final LocalDate fromDate) {
        return postCollectors.parallelStream()
            .map(postCollector -> postCollector.collectNewPosts(fromDate))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }
}
