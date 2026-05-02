package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

    private static final ReservationTime VALID_TIME = new ReservationTime(1L, "10:00");

    @Test
    @DisplayName("유효한 값으로 예약을 생성한다.")
    void should_create_reservation_with_valid_values() {
        final Reservation reservation = new Reservation(1L, "브라운", "2023-08-05", VALID_TIME);

        assertThat(reservation.getName()).isEqualTo("브라운");
        assertThat(reservation.getDate()).isEqualTo("2023-08-05");
    }

    @Test
    @DisplayName("예약자 이름이 null이면 예외가 발생한다.")
    void should_throw_exception_when_name_is_null() {
        assertThatThrownBy(() -> new Reservation(1L, null, "2023-08-05", VALID_TIME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약자 이름은 비어있을 수 없습니다.");
    }

    @Test
    @DisplayName("예약자 이름이 빈 문자열이면 예외가 발생한다.")
    void should_throw_exception_when_name_is_blank() {
        assertThatThrownBy(() -> new Reservation(1L, "  ", "2023-08-05", VALID_TIME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약자 이름은 비어있을 수 없습니다.");
    }

    @Test
    @DisplayName("예약 날짜가 null이면 예외가 발생한다.")
    void should_throw_exception_when_date_is_null() {
        assertThatThrownBy(() -> new Reservation(1L, "브라운", null, VALID_TIME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 날짜는 비어있을 수 없습니다.");
    }

    @Test
    @DisplayName("예약 날짜가 빈 문자열이면 예외가 발생한다.")
    void should_throw_exception_when_date_is_blank() {
        assertThatThrownBy(() -> new Reservation(1L, "브라운", "  ", VALID_TIME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 날짜는 비어있을 수 없습니다.");
    }

    @Test
    @DisplayName("예약 시간이 null이면 예외가 발생한다.")
    void should_throw_exception_when_time_is_null() {
        assertThatThrownBy(() -> new Reservation(1L, "브라운", "2023-08-05", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 시간은 비어있을 수 없습니다.");
    }
}
