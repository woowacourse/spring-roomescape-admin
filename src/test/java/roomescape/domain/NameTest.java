package roomescape.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import roomescape.domain.reservation.Name;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class NameTest {

    @DisplayName("실패 : 예약자의 이름은 1글자 이상이어야 한다")
    @ParameterizedTest
    @NullAndEmptySource
    void should_ReturnIllegalArgumentException_When_NameLengthLessThanMinNameLenth(String invalidName) {
        assertThatThrownBy(() -> new Name(invalidName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 1글자 이상의 영/한글이어야 합니다");
    }

    @DisplayName("실패 : 예약자의 이름은 영/한글로 구성되어야 한다")
    @ParameterizedTest
    @ValueSource(strings = {"1", "a1", "*"})
    void should_ReturnIllegalArgumentException_When_NameIsNotEnglishOrKorean(String invalidName) {
        assertThatThrownBy(() -> new Name(invalidName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 1글자 이상의 영/한글이어야 합니다");
    }

    @DisplayName("성공 : 예약자의 이름은 한글자 이상의 영/한글로 구성된다")
    @ParameterizedTest
    @ValueSource(strings = {"a", "콜리", "sudal"})
    void should_CreateNameObject_When_GiveValidName(String validName) {
        assertThatCode(() -> new Name(validName)).doesNotThrowAnyException();
    }
}