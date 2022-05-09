package hynotice.postcollector.presentation.postcollector.dormitory;

import hynotice.core.domain.Board;
import hynotice.core.domain.Posts;
import hynotice.postcollector.presentation.postcollector.template.PaginationPostCollector;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DormitoryPostCollector extends PaginationPostCollector<DormitoryPost> {

    private static final Map<Board, String> TABLE_NAMES = new HashMap<>();
    private static final String BOARD_URL = "http://www.dormitory.hanyang.ac.kr/board/list";
    private static final String POST_URL_FORMAT = "http://www.dormitory.hanyang.ac.kr/html/community2/board_tab?tb_name=%s&idx=%s";
    private static final String TABLE_NAME_KEY = "tb_name";
    private static final String PAGE_NUMBER_KEY = "pageNum";
    private static final String REQUEST_BODY_FORMAT = "%s=%s&%s=%s";

    static {
        TABLE_NAMES.put(Board.DORMITORY_DIRECT, "direct_notice");
        TABLE_NAMES.put(Board.DORMITORY_HAPPY, "happy_notice");
        TABLE_NAMES.put(Board.DORMITORY_RC, "rc_notice");
        TABLE_NAMES.put(Board.DORMITORY_STRUCTURE, "structure_safe_pds");
        TABLE_NAMES.put(Board.DORMITORY_ENROLLED_STUDENT, "enrolledstudent");
        TABLE_NAMES.put(Board.DORMITORY_FRESHMAN, "freshman");
        TABLE_NAMES.put(Board.DORMITORY_ADDITION, "addition");
    }

    @Override
    protected Predicate<DormitoryPost> getDefaultPostFilter() {
        return dormitoryPost -> true;
    }

    @Override
    protected Set<Board> getBoards() {
        return TABLE_NAMES.keySet();
    }

    @Override
    protected Posts initiatePosts(final Board board, final LocalDate fromDate) {
        return new Posts();
    }

    @Override
    protected List<DormitoryPost> collectAllPostsInPage(final Board board, final int pageNumber)
        throws IOException {
        Document document = getDocument(board, pageNumber);
        Elements posts = document.select("tbody > tr");

        return posts.stream()
            .map(post -> convertToDormitoryPost(post, board))
            .collect(Collectors.toList());
    }

    private DormitoryPost convertToDormitoryPost(final Element post, final Board board) {
        String title = post.select(".sbj").text();
        String href = post.getElementsByTag("a").attr("href");
        String url = getPostUrlByHref(href, board);
        String writingDate = post.select(".listdate").text();

        return new DormitoryPost(title, url, writingDate);
    }

    private String getPostUrlByHref(final String href, final Board board) {
        int startIndex = href.indexOf("(") + 1;
        int endIndex = href.indexOf(")");

        return String.format(POST_URL_FORMAT, TABLE_NAMES.get(board),
            href.substring(startIndex, endIndex));
    }

    private Document getDocument(final Board board, final int pageNumber) throws IOException {
        String requestBody = String.format(REQUEST_BODY_FORMAT, TABLE_NAME_KEY,
            TABLE_NAMES.get(board), PAGE_NUMBER_KEY, pageNumber);
        return Jsoup.connect(BOARD_URL)
            .requestBody(requestBody)
            .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
            .post();
    }
}
