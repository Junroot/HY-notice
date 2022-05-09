package hynotice.postcollector.presentation.postcollector.dormitory;

import hynotice.core.domain.Board;
import hynotice.core.domain.Post;
import hynotice.postcollector.domain.Postable;
import java.time.LocalDate;

public class DormitoryPost implements Postable {

    private static final int WRITING_DATE_LENGTH = 10;
    private final String title;
    private final String url;
    private final String writingDate;

    public DormitoryPost(final String title, final String url, final String writingDate) {
        this.title = title;
        this.url = url;
        this.writingDate = writingDate;
    }

    @Override
    public Post convert(final Board board) {
        return new Post(title, board, url, LocalDate.parse(writingDate.substring(0,
            WRITING_DATE_LENGTH)));
    }
}
