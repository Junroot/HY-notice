package postcollector.domain.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import postcollector.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByWritingDateGreaterThanEqual(final LocalDate localDate);
}
