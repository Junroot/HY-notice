package postcollector.presentation.postcollector.hy;

import java.io.IOException;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import postcollector.domain.Board;
import postcollector.domain.Posts;
import postcollector.exception.CollectingException;
import postcollector.presentation.postcollector.template.PaginationPostCollector;

public class HyPostCollector extends PaginationPostCollector<HyPost> {

    private static final String BASE_URL_HEAD = "https://www.hanyang.ac.kr/web/www/notice_all?p_p_id=viewNotice_WAR_noticeportlet&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_count=1&_viewNotice_WAR_noticeportlet_sKeyType=title";
    private static final String BASE_URL_TAIL = "&_viewNotice_WAR_noticeportlet_sUserId=0&_viewNotice_WAR_noticeportlet_action=view";
    private static final EnumMap<Board, String> URL_FORMATS = new EnumMap<>(Board.class);
    private static final Pattern URL_PATTERN = Pattern.compile("javascript:_viewNotice_WAR_noticeportlet_view_message\\((\\d+)\\);");
    private static final String POST_URL_FORMAT = "https://www.hanyang.ac.kr/web/www/notice_all?p_p_id=viewNotice_WAR_noticeportlet&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_count=1&_viewNotice_WAR_noticeportlet_action=view_message&_viewNotice_WAR_noticeportlet_messageId=%s";

    static {
        URL_FORMATS.put(Board.HY_GRADUATE, BASE_URL_HEAD
            + "&_viewNotice_WAR_noticeportlet_sCategoryId=1&_viewNotice_WAR_noticeportlet_sCurPage=%d"
            + BASE_URL_TAIL);
    }

    @Override
    protected Predicate<HyPost> getDefaultPostFilter() {
        return hyPost -> true;
    }

    @Override
    protected Map<Board, String> getUrlFormats() {
        return URL_FORMATS;
    }

    @Override
    protected Posts initiatePosts(final Board board, final LocalDate fromDate) {
        return new Posts();
    }

    @Override
    protected List<HyPost> collectAllPostsInPage(final Board board, final int pageNumber)
        throws IOException {
        Document document = getDocument(board, pageNumber);
        Elements posts = document.select("#notice01 > div > table > tbody > tr");
        if (posts.size() == 0) {
            throw new CollectingException("게시글을 찾을 수 없습니다.");
        }

        return posts.stream()
            .map(this::convertFromElementToHyPost)
            .collect(Collectors.toList());
    }

    private HyPost convertFromElementToHyPost(final Element element) {
        String title = element.select("td > div > div > p > a").text();
        Matcher href = URL_PATTERN.matcher(element.select("td > div > div > p > a").attr("href"));
        if (!href.find()) {
            throw new CollectingException("href 링크를 추출하는데 실패했습니다.");
        }
        String url = String.format(POST_URL_FORMAT, href.group(1));
        String writingDate = element.getElementsByClass("notice-date").text();
        return new HyPost(title, url, writingDate);
    }


    private Document getDocument(final Board board, final int pageNumber) throws IOException {
        String urlFormat = URL_FORMATS.get(board);
        String url = String.format(urlFormat, pageNumber);
        return Jsoup.connect(url).get();
    }
}
