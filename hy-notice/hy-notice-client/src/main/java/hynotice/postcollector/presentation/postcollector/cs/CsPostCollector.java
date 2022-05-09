package hynotice.postcollector.presentation.postcollector.cs;

import hynotice.core.domain.Board;
import hynotice.core.domain.Posts;
import java.io.IOException;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import hynotice.postcollector.exception.CollectingException;
import hynotice.postcollector.presentation.postcollector.template.PaginationPostCollector;

@Component
public class CsPostCollector extends PaginationPostCollector<CsPost> {

    private static final String BASE_URL = "http://cs.hanyang.ac.kr";
    private static final EnumMap<Board, String> URL_FORMATS = new EnumMap<>(Board.class);
    private static final int POST_NUMBER_INDEX = 1;
    private static final int POST_TITLE_INDEX = 2;
    private static final int POST_WRITING_DATE_INDEX = 4;

    static {
        URL_FORMATS.put(Board.CS_INFO, "%s/board/info_board.php?ptype=&page=%d&code=notice");
        URL_FORMATS.put(Board.CS_JOB, "%s/board/job_board.php?ptype=&page=%d&code=job_board");
        URL_FORMATS.put(Board.CS_GRADUATE, "%s/board/gradu_board.php?ptype=&page=%d&code=gradu_board");
    }

    @Override
    protected Predicate<CsPost> getDefaultPostFilter() {
        return CsPost::isNotNotice;
    }

    @Override
    protected Set<Board> getBoards() {
        return URL_FORMATS.keySet();
    }

    @Override
    protected Posts initiatePosts(final Board board, final LocalDate fromDate) {
        return collectPosts(board, 1, CsPost::isNotice)
            .filterEqualOrAfter(fromDate);
    }

    @Override
    protected List<CsPost> collectAllPostsInPage(final Board board, final int pageNumber)
        throws IOException {
        Document document = getDocument(board, pageNumber);
        Elements posts = document.select("table.bbs_con > tbody > tr");
        if (posts.size() == 0) {
            throw new CollectingException("게시글을 찾을 수 없습니다.");
        }

        return posts.stream()
            .map(this::convertFromElementToCsPost)
            .collect(Collectors.toList());
    }

    private Document getDocument(final Board board, final int pageNumber) throws IOException {
        String urlFormat = URL_FORMATS.get(board);
        String url = String.format(urlFormat, BASE_URL, pageNumber);
        return Jsoup.connect(url).get();
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
