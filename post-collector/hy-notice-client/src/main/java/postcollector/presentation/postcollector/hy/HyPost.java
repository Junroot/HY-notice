package postcollector.presentation.postcollector.hy;

import core.domain.Board;
import core.domain.Post;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import postcollector.domain.Postable;

public class HyPost implements Postable {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    private final String title;
    private final String url;
    private final String writingDate;

    public HyPost(final String title, final String url, final String writingDate) {
        this.title = title;
        this.url = url;
        this.writingDate = writingDate;
    }

    @Override
    public Post convert(final Board board) {
        return new Post(title, board, url, LocalDate.parse(writingDate, DATE_TIME_FORMATTER));
    }
}
