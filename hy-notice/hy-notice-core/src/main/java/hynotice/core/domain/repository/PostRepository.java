package hynotice.core.domain.repository;

import hynotice.core.domain.Post;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByWritingDateGreaterThanEqual(final LocalDate localDate);
}
