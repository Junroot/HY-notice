package postcollector.configuration;

import java.util.Objects;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import postcollector.domain.Board;

@Converter(autoApply = true)
public class BoardDbConverter implements AttributeConverter<Board, Long> {

    @Override
    public Long convertToDatabaseColumn(final Board attribute) {
        if (Objects.isNull(attribute)) {
            return null;
        }
        return attribute.getId();
    }

    @Override
    public Board convertToEntityAttribute(final Long dbData) {
        if (Objects.isNull(dbData)) {
            return null;
        }
        return Board.getById(dbData);
    }
}
