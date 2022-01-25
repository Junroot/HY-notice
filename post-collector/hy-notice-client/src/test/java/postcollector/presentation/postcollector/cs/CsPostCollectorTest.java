package postcollector.presentation.postcollector.cs;

import static org.assertj.core.api.Assertions.assertThat;

import core.domain.Post;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import postcollector.presentation.postcollector.httpconnection.CustomConnection;

@DisplayName("한양대 컴퓨터소프트웨어학부 게시글 수집")
class CsPostCollectorTest {

    @DisplayName("모든 게시글 수집")
    @Test
    void collectNewPosts() throws IOException {
        File page1File = new File("./src/test/resources/cs_page1.html");
        File page2File = new File("./src/test/resources/cs_page2.html");
        Document document1 = Jsoup.parse(page1File, "euc-kr");
        Document document2 = Jsoup.parse(page2File, "euc-kr");
        try (MockedStatic<Jsoup> jsoup = Mockito.mockStatic(Jsoup.class)) {
            jsoup.when(() -> Jsoup.connect(
                    "http://cs.hanyang.ac.kr/board/info_board.php?ptype=&page=1&code=notice"))
                .thenReturn(new CustomConnection(document1));
            jsoup.when(() -> Jsoup.connect(
                    "http://cs.hanyang.ac.kr/board/job_board.php?ptype=&page=1&code=job_board"))
                .thenReturn(new CustomConnection(document1));
            jsoup.when(() -> Jsoup.connect(
                    "http://cs.hanyang.ac.kr/board/gradu_board.php?ptype=&page=1&code=gradu_board"))
                .thenReturn(new CustomConnection(document1));

            jsoup.when(() -> Jsoup.connect(
                    "http://cs.hanyang.ac.kr/board/info_board.php?ptype=&page=2&code=notice"))
                .thenReturn(new CustomConnection(document2));
            jsoup.when(() -> Jsoup.connect(
                    "http://cs.hanyang.ac.kr/board/job_board.php?ptype=&page=2&code=job_board"))
                .thenReturn(new CustomConnection(document2));
            jsoup.when(() -> Jsoup.connect(
                    "http://cs.hanyang.ac.kr/board/gradu_board.php?ptype=&page=2&code=gradu_board"))
                .thenReturn(new CustomConnection(document2));

            CsPostCollector csPostCollector = new CsPostCollector();
            List<Post> posts = csPostCollector.collectNewPosts(LocalDate.of(2021, 11, 24));

            assertThat(posts).hasSize(12);
        }
    }
}
