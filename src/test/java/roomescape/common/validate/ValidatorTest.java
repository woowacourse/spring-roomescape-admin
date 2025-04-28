package roomescape.common.validate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

class ValidatorTest {

    @Test
    @DisplayName("필드가 null이면 IllegalArgumentException이 발생한다")
    void validateNotNullFieldThrowsException() {
        final Validator validator = Validator.of(SampleClass.class);

        assertThatThrownBy(() -> validator.notNullField("sampleField", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("SampleClass.sampleField 은(는) null일 수 없습니다.");
    }

    @Test
    @DisplayName("필드가 null이 아니면 예외가 발생하지 않는다")
    void validateNotNullFieldPasses() {
        final Validator validator = Validator.of(SampleClass.class);

        assertThat(validator.notNullField("sampleField", "")).isNotNull();
    }

    @Test
    @DisplayName("필드가 null이거나 blank이면 IllegalArgumentException이 발생한다")
    void validateNotBlankFieldThrowsException() {
        final Validator validator = Validator.of(SampleClass.class);

        assertThatThrownBy(() -> validator.notBlankField("sampleField", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("SampleClass.sampleField 은(는) 비어있을 수 없습니다.");

        assertThatThrownBy(() -> validator.notBlankField("sampleField", " "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("SampleClass.sampleField 은(는) 비어있을 수 없습니다.");
    }

    @Test
    @DisplayName("필드가 blank가 아니면 예외가 발생하지 않는다")
    void validateNotBlankFieldPasses() {
        final Validator validator = Validator.of(SampleClass.class);

        assertThat(validator.notBlankField("sampleField", "content")).isNotNull();
    }

    static class SampleClass {
        private String sampleField;
    }
}
