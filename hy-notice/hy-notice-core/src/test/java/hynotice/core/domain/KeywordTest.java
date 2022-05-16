package hynotice.core.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import hynotice.core.exception.ExpectedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class KeywordTest {

    @DisplayName("잘못된 키워드를 입력하면 예외가 발생한다.")
    @ValueSource(strings = {"", "공 백", " 공백", "공백 ", "@특수문자", "점.점"})
    @ParameterizedTest
    void InvalidKeywordTest(final String value) {
        assertThatThrownBy(() -> new Keyword(value)).isInstanceOf(ExpectedException.class);
    }

    @DisplayName("정상 키워드를 입력하면 예외가 발생하지 않는다.")
    @ValueSource(strings = {"정sang", "2020테스트", "2020test", "test", "테스트", "2022"})
    @ParameterizedTest
    void validKeywordTest(final String value) {
        assertThatCode(() -> new Keyword(value)).doesNotThrowAnyException();
    }
}
