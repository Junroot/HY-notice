package postcollector.presentation.postcollector.template;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import core.domain.Board;
import core.domain.Post;
import core.domain.Posts;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import postcollector.domain.Postable;
import postcollector.exception.CollectingException;
import postcollector.presentation.postcollector.PostCollector;

public abstract class PaginationPostCollector<T extends Postable> implements PostCollector {

    @Override
    public List<Post> collectNewPosts(final LocalDate fromDate) {
        Posts result = new Posts();
        Map<Board, String> urlFormats = getUrlFormats();
        for (Board board : urlFormats.keySet()) {
            result.addAll(collectNewPostsFromBoard(board, fromDate));
        }

        return result.getElements();
    }

    private Posts collectNewPostsFromBoard(final Board board, final LocalDate fromDate) {
        Posts result = initiatePosts(board, fromDate);
        for (int pageNumber = 1; ; pageNumber++) {
            Posts posts = collectPosts(board, pageNumber, getDefaultPostFilter())
                .filterEqualOrAfter(fromDate);
            if (posts.isEmpty()) {
                break;
            }
            result.addAll(posts);
        }
        return result;
    }

    protected Posts collectPosts(final Board board, final int pageNumber, final Predicate<T> filter) {
        try {
            return collectAllPostsInPage(board, pageNumber).stream()
                .filter(filter)
                .map(csPost -> csPost.convert(board))
                .collect(collectingAndThen(toList(), Posts::new));
        } catch (IOException e) {
            throw new CollectingException("요청을 보내는데 실패했습니다.");
        }
    }

    protected abstract Predicate<T> getDefaultPostFilter();

    protected abstract Map<Board, String> getUrlFormats();

    protected abstract Posts initiatePosts(final Board board, final LocalDate fromDate);

    protected abstract List<T> collectAllPostsInPage(final Board board, final int pageNumber)
        throws IOException;
}
