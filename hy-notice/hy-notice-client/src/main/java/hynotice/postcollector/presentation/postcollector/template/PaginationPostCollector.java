package hynotice.postcollector.presentation.postcollector.template;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import hynotice.core.domain.Board;
import hynotice.core.domain.Post;
import hynotice.core.domain.Posts;
import hynotice.postcollector.domain.Postable;
import hynotice.postcollector.exception.CollectingException;
import hynotice.postcollector.presentation.postcollector.PostCollector;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public abstract class PaginationPostCollector<T extends Postable> implements PostCollector {

    @Override
    public List<Post> collectNewPosts(final LocalDate fromDate) {
        Posts result = new Posts();
        Set<Board> boards = getBoards();
        for (Board board : boards) {
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

    protected Posts collectPosts(final Board board, final int pageNumber,
                                 final Predicate<T> filter) {
        try {
            return collectAllPostsInPage(board, pageNumber).stream()
                .filter(filter)
                .map(post -> post.convert(board))
                .collect(collectingAndThen(toList(), Posts::new));
        } catch (IOException e) {
            throw new CollectingException("요청을 보내는데 실패했습니다.");
        }
    }

    protected abstract Predicate<T> getDefaultPostFilter();

    protected abstract Set<Board> getBoards();

    protected abstract Posts initiatePosts(final Board board, final LocalDate fromDate);

    protected abstract List<T> collectAllPostsInPage(final Board board, final int pageNumber)
        throws IOException;
}
