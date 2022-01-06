package postcollector.presentation.postcollector.hy;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import postcollector.domain.Post;

public class HyPostCollectorTest {

    @DisplayName("모든 게시글 수집")
    @Test
    void collectNewPosts() throws IOException {
        HyPostCollector hyPostCollector = new HyPostCollector();
        List<Post> posts = hyPostCollector.collectNewPosts(LocalDate.of(2022, 1, 6));

        assertThat(posts.size() > 0).isTrue();
    }
}
