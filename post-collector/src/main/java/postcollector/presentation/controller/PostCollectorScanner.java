package postcollector.presentation.controller;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import postcollector.domain.Post;
import postcollector.presentation.postcollector.cs.CsPostCollector;

public class PostCollectorScanner {

    private final List<PostCollector> postCollectors;

    public PostCollectorScanner() {
        postCollectors = List.of(new CsPostCollector());
    }

    public List<Post> collectNewPosts(final LocalDate fromDate) {
        return postCollectors.parallelStream()
            .map(postCollector -> postCollector.collectNewPosts(fromDate))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }
}
