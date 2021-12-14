package postcollector.domain;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class Post {

    private Long id;
    private String title;
    private String url;
    private LocalDate writingDate;

    public Post(final String title, final String url, final LocalDate writingDate) {
        this(null, title, url, writingDate);
    }

    public Post(final Long id, final String title, final String url, final LocalDate writingDate) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.writingDate = writingDate;
    }
}
