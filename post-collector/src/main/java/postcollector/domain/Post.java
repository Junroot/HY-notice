package postcollector.domain;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    private String title;
    private Board board;
    @Lob
    @Column
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
