package hynotice.core.domain.repository;

import hynotice.core.domain.Keyword;
import hynotice.core.domain.Post;
import java.util.List;

public interface PostCustomRepository {

    List<Post> findAllByAnyKeyword(List<Keyword> keywords, int page, int pageSize);
}
