package postcollector.domain;

import java.time.LocalDateTime;
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
public class CollectingMetaData {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @CreatedDate
    private LocalDateTime collectingTime;
    private Long collectingCount;

    public CollectingMetaData(final Long collectingCount) {
        this(null, collectingCount);
    }

    public CollectingMetaData(final Long id, final Long collectingCount) {
        this.id = id;
        this.collectingCount = collectingCount;
    }
}
