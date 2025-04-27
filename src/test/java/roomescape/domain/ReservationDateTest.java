package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.exception.EmptyReservationDateException;

class ReservationDateTest {

    @DisplayName("날짜로 객체를 생성할 수 있다.")
    @Test
    void createReservationDate() {
        // given

        // when & then
        assertThatCode(() -> new ReservationDate(LocalDate.of(2025, 1, 1)))
                .doesNotThrowAnyException();
    }

    @DisplayName("날짜가 null이면 예외가 발생한다.")
    @Test
    void createNullDate() {
        // given

        // when & then
        assertThatThrownBy(() -> new ReservationDate(null))
                .isInstanceOf(EmptyReservationDateException.class);
    }
}
