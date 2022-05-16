package hynotice.core.domain;

import hynotice.core.exception.ExpectedException;
import java.util.regex.Pattern;

public class Keyword {

    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 20;
    private static final Pattern PATTERN = Pattern.compile("^[ㄱ-ㅎ가-힣a-zA-Z0-9]+$");
    private final String value;

    public Keyword(final String value) {
        validate(value);
        this.value = value;
    }

    private static void validate(final String value) {
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new ExpectedException(
                String.format("키워드의 길이는 %d 이상 %d 이하입니다.", MIN_LENGTH, MAX_LENGTH)
            );
        }
        if (!PATTERN.matcher(value).matches()) {
            throw new ExpectedException("키워드로는 한글, 영어, 숫자만 사용가능합니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
