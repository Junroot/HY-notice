package hynotice.api.presentation.dto;

import hynotice.core.domain.Post;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

    private Long id;
    private String title;
    private String board;
    private String url;
    private LocalDate writingDate;
    private List<String> containingKeywords;

    public static PostResponse from(final Post post, final List<String> keywords) {
        return new PostResponse(
            post.getId(),
            post.getTitle(),
            post.getBoard().getName(),
            post.getUrl(),
            post.getWritingDate(),
            keywords
        );
    }
}
