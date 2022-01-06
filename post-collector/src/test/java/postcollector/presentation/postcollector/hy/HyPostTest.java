package postcollector.presentation.postcollector.hy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import postcollector.domain.Board;
import postcollector.domain.Post;

class HyPostTest {

    @DisplayName("날짜 문자열을 DateTime으로 변환한다.")
    @Test
    void convert() {
        HyPost hyPost = new HyPost("제목", "http://www.hanyang.ac.kr/surl/HTZjB", "2022/01/06");
        Post result = hyPost.convert(Board.HY_GRADUATE);

        assertThat(result.getWritingDate()).isEqualTo(LocalDate.of(2022, 1, 6));
    }
}
