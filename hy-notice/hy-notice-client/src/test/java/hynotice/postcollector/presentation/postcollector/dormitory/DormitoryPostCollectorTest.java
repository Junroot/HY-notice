package hynotice.postcollector.presentation.postcollector.dormitory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import hynotice.core.domain.Post;
import hynotice.postcollector.presentation.postcollector.httpconnection.CustomConnection;
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

public class DormitoryPostCollectorTest {

    @DisplayName("모든 게시글 수집")
    @Test
    void collectNewPosts() throws IOException {
        File page1File = new File("./src/test/resources/dormitory_page1.html");
        File page2File = new File("./src/test/resources/dormitory_page2.html");

        Document document1 = Jsoup.parse(page1File, "UTF-8");
        Document document2 = Jsoup.parse(page2File, "UTF-8");

        try (MockedStatic<Jsoup> jsoup = Mockito.mockStatic(Jsoup.class)) {
            jsoup.when(() -> Jsoup.connect(any()))
                .thenAnswer(answer -> new CustomConnection(List.of(document1, document2)));

            DormitoryPostCollector dormitoryPostCollector = new DormitoryPostCollector();
            List<Post> posts = dormitoryPostCollector.collectNewPosts(LocalDate.of(2021, 1, 1));

            assertThat(posts).hasSize(28);
        }
    }
}
