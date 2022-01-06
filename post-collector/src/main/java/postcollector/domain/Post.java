package postcollector.domain;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class Post {

    private Long id;
    private String title;
    private Board board;
    private String url;
    private LocalDate writingDate;

    public Post(final String title, final Board board, final String url,
                final LocalDate writingDate) {
        this(null, title, board, url, writingDate);
    }

    public Post(final Long id, final String title, final Board board, final String url,
                final LocalDate writingDate) {
        this.id = id;
        this.title = title;
        this.board = board;
        this.url = url;
        this.writingDate = writingDate;
    }

    public boolean isEqualOrAfter(final LocalDate localDate) {
        return writingDate.isAfter(localDate) || writingDate.isEqual(localDate);
    }
}
