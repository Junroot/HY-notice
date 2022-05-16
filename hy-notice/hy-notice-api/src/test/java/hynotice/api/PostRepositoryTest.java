package hynotice.api;

import static org.assertj.core.api.Assertions.assertThat;

import hynotice.core.domain.Board;
import hynotice.core.domain.Keyword;
import hynotice.core.domain.Post;
import hynotice.core.domain.repository.PostRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @DisplayName("검색어 중 하나라도 포함하고 있는 게시글 검색")
    @Test
    void findAllByAnyKeyword() {
        postRepository.save(
            new Post("한양대 공지", Board.HY_SCHOLARSHIP, "https://google.com", LocalDate.now()));
        postRepository.save(
            new Post("새로운 공지입니다.", Board.HY_SCHOLARSHIP, "https://google2.com", LocalDate.now()));
        postRepository.save(
            new Post("이 글을 검색하지마세요.", Board.HY_SCHOLARSHIP, "https://google3.com",
                LocalDate.now()));

        assertThat(postRepository.findAllByAnyKeyword(
            List.of(new Keyword("한양대"), new Keyword("공지")),
            0, 12)
        ).hasSize(2);
    }
}
