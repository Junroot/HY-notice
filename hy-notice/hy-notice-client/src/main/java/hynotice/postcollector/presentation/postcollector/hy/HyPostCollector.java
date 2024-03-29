package hynotice.postcollector.presentation.postcollector.hy;

import hynotice.core.domain.Board;
import hynotice.core.domain.Posts;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import hynotice.postcollector.exception.CollectingException;
import hynotice.postcollector.presentation.postcollector.template.PaginationPostCollector;

@Component
public class HyPostCollector extends PaginationPostCollector<HyPost> {

    private static final String BASE_URL_HEAD = "https://www.hanyang.ac.kr/web/www/notice_all?p_p_id=viewNotice_WAR_noticeportlet&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_count=1&_viewNotice_WAR_noticeportlet_sKeyType=title";
    private static final String BASE_URL_TAIL = "&_viewNotice_WAR_noticeportlet_sUserId=0&_viewNotice_WAR_noticeportlet_action=view";
    private static final EnumMap<Board, String> URL_FORMATS = new EnumMap<>(Board.class);
    private static final Pattern URL_PATTERN = Pattern.compile(
        "javascript:_viewNotice_WAR_noticeportlet_view_message\\((\\d+)\\);");
    private static final String POST_URL_FORMAT = "https://www.hanyang.ac.kr/web/www/notice_all?p_p_id=viewNotice_WAR_noticeportlet&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_count=1&_viewNotice_WAR_noticeportlet_action=view_message&_viewNotice_WAR_noticeportlet_messageId=%s";
    private static final List<Board> BOARDS = Arrays.asList(Board.HY_GRADUATE, Board.HY_ADMISSION,
        Board.HY_RECRUITMENT, Board.HY_SERVICE, Board.HY_PUBLIC, Board.HY_INDUSTRY,
        Board.HY_EVENT, Board.HY_SCHOLARSHIP, Board.HY_CONFERENCE);

    static {
        for (int i = 0; i < BOARDS.size(); i++) {
            URL_FORMATS.put(BOARDS.get(i),
                BASE_URL_HEAD + "&_viewNotice_WAR_noticeportlet_sCategoryId=" + (i + 1)
                    + "&_viewNotice_WAR_noticeportlet_sCurPage=%d" + BASE_URL_TAIL);
        }
    }

    @Override
    protected Predicate<HyPost> getDefaultPostFilter() {
        return hyPost -> true;
    }

    @Override
    protected Set<Board> getBoards() {
        return URL_FORMATS.keySet();
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
