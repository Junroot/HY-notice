package postcollector.presentation.postcollector.cs;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import postcollector.domain.Board;
import postcollector.domain.Post;
import postcollector.domain.Posts;
import postcollector.exception.CollectingException;
import postcollector.presentation.controller.PostCollector;

public class CsPostCollector implements PostCollector {

    private static final String BASE_URL = "http://cs.hanyang.ac.kr/board";
    private static final EnumMap<Board, String> urlFormats = new EnumMap<>(Board.class);
    private static final int POST_NUMBER_INDEX = 1;
    private static final int POST_TITLE_INDEX = 2;
    private static final int POST_WRITING_DATE_INDEX = 4;

    static {
        urlFormats.put(Board.CS_INFO, "%s/info_board.php?ptype=&page=%d&code=notice");
        urlFormats.put(Board.CS_JOB, "%s/job_board.php?ptype=&page=%d&code=job_board");
        urlFormats.put(Board.CS_GRADUATION, "%s/gradu_board.php?ptype=&page=%d&code=gradu_board");
    }

    @Override
    public List<Post> collectNewPosts(final LocalDate fromDate) {
        Posts result = new Posts();
        for (Board board : urlFormats.keySet()) {
            result.addAll(collectNewPostsFromBoard(board, fromDate));
        }

        return result.getElements();
    }

    private Posts collectNewPostsFromBoard(final Board board, final LocalDate fromDate) {
        Posts result = collectPosts(board, 1, CsPost::isNotice)
            .filterEqualOrAfter(fromDate);
        for (int pageNumber = 1; ; pageNumber++) {
            Posts posts = collectPosts(board, pageNumber, CsPost::isNotNotice)
                .filterEqualOrAfter(fromDate);
            if (posts.isEmpty()) {
                break;
            }
            result.addAll(posts);
        }
        return result;
    }

    private Posts collectPosts(final Board board, final int pageNumber,
                               final Predicate<CsPost> filter) {
        return collectAllCsPosts(board, pageNumber).stream()
            .filter(filter)
            .map(csPost -> csPost.convertToPost(board))
            .collect(collectingAndThen(toList(), Posts::new));
    }

    private List<CsPost> collectAllCsPosts(final Board board, final int pageNumber) {
        try {
            String urlFormat = urlFormats.get(board);
            String url = String.format(urlFormat, BASE_URL, pageNumber);
            Document document = Jsoup.connect(url).get();
            Elements posts = document.select("table.bbs_con > tbody > tr");
            if (posts.size() == 0) {
                throw new CollectingException("게시글을 찾을 수 없습니다.");
            }

            return posts.stream()
                .map(this::convertFromElementToCsPost)
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
