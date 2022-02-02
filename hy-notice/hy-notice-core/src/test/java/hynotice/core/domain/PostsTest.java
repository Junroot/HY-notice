package hynotice.core.domain;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostsTest {

    @DisplayName("특정 날짜 이후의 게시글만 필터링한다.")
    @Test
    void filterEqualOrAfter() {
        Posts posts = new Posts(List.of(
            new Post("제목1", Board.CS_GRADUATE,
                "http://cs.hanyang.ac.kr/board/info_board.php?ptype=view&idx=29596&page=2&code=notice",
                LocalDate.of(2022, 1, 1)),
            new Post("제목2", Board.CS_GRADUATE,
                "http://cs.hanyang.ac.kr/board/info_board.php?ptype=view&idx=29596&page=2&code=notice",
                LocalDate.of(2022, 1, 2)),
            new Post("제목3", Board.CS_GRADUATE,
                "http://cs.hanyang.ac.kr/board/info_board.php?ptype=view&idx=29596&page=2&code=notice",
                LocalDate.of(2022, 1, 3)),
            new Post("제목4", Board.CS_GRADUATE,
                "http://cs.hanyang.ac.kr/board/info_board.php?ptype=view&idx=29596&page=2&code=notice",
                LocalDate.of(2022, 2, 1))
        ));
        Posts result = posts.filterEqualOrAfter(LocalDate.of(2022, 1, 3));

        assertThat(result.getElements()).hasSize(2);
    }
}
