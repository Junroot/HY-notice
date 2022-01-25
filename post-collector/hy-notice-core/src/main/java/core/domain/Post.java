package core.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String title;
    private Board board;
    @Column(unique = true, length = 511)
    private String url;
    private LocalDate writingDate;
    @CreatedDate
    private LocalDateTime createdAt;

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

    public boolean isEqualOrAfterDate(final LocalDate localDate) {
        return writingDate.isAfter(localDate) || writingDate.isEqual(localDate);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Post post = (Post) o;
        return url.equals(post.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
