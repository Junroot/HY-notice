package hynotice.postcollector.presentation.postcollector.hy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import hynotice.core.domain.Post;
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
import hynotice.postcollector.presentation.postcollector.httpconnection.CustomConnection;

public class HyPostCollectorTest {

    @DisplayName("모든 게시글 수집")
    @Test
    void collectNewPosts() throws IOException {
        File page1File = new File("./src/test/resources/hy_page1.html");
        File page2File = new File("./src/test/resources/hy_page2.html");

        Document document1 = Jsoup.parse(page1File, "UTF-8");
        Document document2 = Jsoup.parse(page2File, "UTF-8");

        try (MockedStatic<Jsoup> jsoup = Mockito.mockStatic(Jsoup.class)) {
            jsoup.when(() -> Jsoup.connect(any())).thenAnswer(answer -> {
                String url = answer.getArgument(0);
                if (url.contains("_viewNotice_WAR_noticeportlet_sCurPage=1")) {
                    return new CustomConnection(document1);
                }
                return new CustomConnection(document2);
            });
            HyPostCollector hyPostCollector = new HyPostCollector();
            List<Post> posts = hyPostCollector.collectNewPosts(LocalDate.of(2021, 12, 29));

            assertThat(posts).hasSize(63);
        }
    }
}
