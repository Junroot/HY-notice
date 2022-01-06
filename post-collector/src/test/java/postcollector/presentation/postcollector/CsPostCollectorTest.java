package postcollector.presentation.postcollector;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import postcollector.domain.Post;
import postcollector.presentation.postcollector.cs.CsPostCollector;

@DisplayName("한양대 컴퓨터소프트웨어학부 게시글 수집")
class CsPostCollectorTest {

    @DisplayName("모든 게시글 수집")
    @Test
    void collectNewPosts() {
        CsPostCollector csPostCollector = new CsPostCollector();
        List<Post> posts = csPostCollector.collectNewPosts(LocalDate.of(2022, 1, 1));

        assertThat(posts.size() > 0).isTrue();
    }
}
