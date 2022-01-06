package postcollector.domain;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostTest {

    @DisplayName("게시글이 특정 날짜 이후인 경우에만 참을 반환한다.")
    @Test
    void isEqualOrAfter() {
        Post post = new Post("제목", Board.CS_GRADUATION,
            "http://cs.hanyang.ac.kr/board/info_board.php?ptype=view&idx=29596&page=2&code=notice",
            LocalDate.of(2022, 1, 1));

        assertThat(post.isEqualOrAfter(LocalDate.of(2021, 12, 31))).isTrue();
        assertThat(post.isEqualOrAfter(LocalDate.of(2022, 1, 1))).isTrue();
        assertThat(post.isEqualOrAfter(LocalDate.of(2022, 1, 2))).isFalse();
    }
}
