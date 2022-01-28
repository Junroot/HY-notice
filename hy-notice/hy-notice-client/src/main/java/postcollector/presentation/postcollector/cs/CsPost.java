package postcollector.presentation.postcollector.cs;

import core.domain.Board;
import core.domain.Post;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import postcollector.domain.Postable;

public class CsPost implements Postable {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yy.MM.dd");

    private final String number;
    private final String title;
    private final String url;
    private final String writingDate;

    public CsPost(final String number, final String title, final String url,
                  final String writingDate) {
        this.number = number;
        this.title = title;
        this.url = url;
        this.writingDate = writingDate;
    }

    public boolean isNotNotice() {
        return !isNotice();
    }

    public boolean isNotice() {
        try {
            int intValue = Integer.parseInt(number);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    @Override
    public Post convert(final Board board) {
        return new Post(title, board, url, LocalDate.parse(writingDate, DATE_TIME_FORMATTER));
    }
}
