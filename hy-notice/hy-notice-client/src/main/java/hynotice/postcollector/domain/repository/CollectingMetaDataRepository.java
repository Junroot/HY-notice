package hynotice.postcollector.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import hynotice.postcollector.domain.CollectingMetaData;

public interface CollectingMetaDataRepository extends JpaRepository<CollectingMetaData, Long> {

    Optional<CollectingMetaData> findFirstByOrderByCollectingTimeDesc();
}
