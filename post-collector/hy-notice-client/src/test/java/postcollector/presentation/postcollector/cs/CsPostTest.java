package postcollector.presentation.postcollector.cs;

import static org.assertj.core.api.Java6Assertions.assertThat;

import core.domain.Board;
import core.domain.Post;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CsPostTest {

    private CsPost normalPost;
    private CsPost noticePost;

    @BeforeEach
    void setUp() {
        normalPost = new CsPost("2345", "게시글 제목",
            "http://cs.hanyang.ac.kr/board/info_board.php?ptype=view&idx=29596&page=2&code=notice",
            "21.11.24");
        noticePost = new CsPost("[공지]", "게시글 제목",
            "http://cs.hanyang.ac.kr/board/info_board.php?ptype=view&idx=29596&page=2&code=notice",
            "21.11.24");
    }

    @DisplayName("게시글 번호에 '[공지]'라고 적혀있을 경우에만 참을 반환한다.")
    @Test
    void isNotNotice() {
        assertThat(normalPost.isNotNotice()).isTrue();
        assertThat(noticePost.isNotNotice()).isFalse();
    }

    @DisplayName("게시글 번호에 '[공지]'라고 적혀있을 경우 참을 반환한다.")
    @Test
    void isNotice() {
        assertThat(normalPost.isNotice()).isFalse();
        assertThat(noticePost.isNotice()).isTrue();
    }

    @DisplayName("날짜 문자열을 DateTime으로 변환한다.")
    @Test
    void convert() {
        CsPost csPost = new CsPost("1245", "제목",
            "http://cs.hanyang.ac.kr/board/info_board.php?ptype=view&idx=29596&page=2&code=notice",
            "21.03.24");
        Post result = csPost.convert(Board.CS_INFO);

        assertThat(result.getWritingDate()).isEqualTo(LocalDate.of(2021, 3, 24));
    }
}
