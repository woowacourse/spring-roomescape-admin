package roomescape.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import roomescape.exception.InvalidReservationException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class NameTest {
    @DisplayName("이름은 1자 미만, 5자 초과일 경우 예외를 발생시킨다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "linirini"})
    void invalidNameLength(String name) {
        //given
        String date = "2024-10-04";
        String time = "10:00";

        //when&then
        assertThatThrownBy(() -> new Reservation(name, date, time))
                .isInstanceOf(InvalidReservationException.class)
                .hasMessage("이름은 1자 이상, 5자 이하여야 합니다.");
    }
}
