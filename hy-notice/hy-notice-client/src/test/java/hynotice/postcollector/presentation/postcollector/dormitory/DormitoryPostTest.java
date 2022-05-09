package hynotice.postcollector.presentation.postcollector.dormitory;

import static org.assertj.core.api.Assertions.assertThat;

import hynotice.core.domain.Board;
import hynotice.core.domain.Post;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DormitoryPostTest {

    @DisplayName("기숙사 게시글의 날짜 형식을 DateTime으로 반환한다.")
    @Test
    void convert() {
        DormitoryPost dormitoryPost = new DormitoryPost("title", "url", "2021-12-10 14:49:37");
        Post post = dormitoryPost.convert(Board.DORMITORY_ADDITION);
        LocalDate writingDate = post.getWritingDate();

        assertThat(writingDate.getYear()).isEqualTo(2021);
        assertThat(writingDate.getMonthValue()).isEqualTo(12);
        assertThat(writingDate.getDayOfMonth()).isEqualTo(10);
    }
}
