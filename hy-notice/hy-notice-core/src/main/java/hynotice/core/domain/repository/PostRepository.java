package hynotice.core.domain.repository;

import hynotice.core.domain.Post;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository {

    List<Post> findAllByWritingDateGreaterThanEqual(final LocalDate localDate);
}
