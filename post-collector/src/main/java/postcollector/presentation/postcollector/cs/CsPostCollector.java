package postcollector.presentation.postcollector.cs;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import postcollector.domain.Post;
import postcollector.exception.CollectingException;
import postcollector.presentation.controller.PostCollector;

public class CsPostCollector implements PostCollector {

    private static final String BASE_URL = "http://cs.hanyang.ac.kr";
    private static final String URL_FORMAT = "%s/board/info_board.php?ptype=&page=%d&code=notice";
    private static final int POST_NUMBER_INDEX = 1;
    private static final int POST_TITLE_INDEX = 2;
    private static final int POST_WRITING_DATE_INDEX = 4;

    @Override
    public List<Post> collectNewPosts() {
        try {
            Document document = Jsoup.connect(String.format(URL_FORMAT, BASE_URL, 1)).get();
            Elements posts = document.select("table.bbs_con > tbody > tr");
            if (posts.size() == 0) {
                throw new CollectingException("게시글을 찾을 수 없습니다.");
            }

            return posts.stream()
                .map(this::convertFromElementToCsPost)
                .filter(CsPost::isNotNotice)
                .map(CsPost::convertToPost)
                .collect(Collectors.toList());
        } catch (IOException e) {
            throw new CollectingException("요청을 보내는데 실패했습니다.");
        }
    }

    private CsPost convertFromElementToCsPost(final Element post) {
        Elements columns = post.select("td");
        String postNumber = columns.get(POST_NUMBER_INDEX).text();
        String postTitle = columns.get(POST_TITLE_INDEX).text();
        String postUrl = columns.get(POST_TITLE_INDEX).getElementsByTag("a").attr("href");
        String writingDate = columns.get(POST_WRITING_DATE_INDEX).text();

        return new CsPost(postNumber, postTitle, BASE_URL + postUrl, writingDate);
    }
}
