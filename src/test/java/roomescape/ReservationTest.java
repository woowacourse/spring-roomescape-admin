package roomescape;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.time.ReservationTime;

public class ReservationTest {
    @DisplayName("예약 객체를 생성한다.")
    @Test
    void 객체_정상_생성_테스트() {
        // given
        ReservationTime time = new ReservationTime(1L, LocalTime.of(10, 0));
        Reservation reservation = new Reservation(1L, "brown", LocalDate.now(), time);

        // when & then
        assertThat(reservation.getName()).isEqualTo("brown");
        assertThat(reservation.getDate()).isEqualTo(LocalDate.now());
        assertThat(reservation.getTime()).isEqualTo(time);
    }

    @DisplayName("예약자 이름이 비어 있는 경우, IllegalArgumentException이 발생한다.")
    @Test
    void 예약자_이름_빈칸_예외_테스트() {
        // given
        ReservationTime time = new ReservationTime(1L, LocalTime.of(10, 0));

        // when & then
        assertThatThrownBy(() -> new Reservation(1L, "", LocalDate.now(), time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 예약자의 이름은 비어 있거나, 공백일 수 없습니다.");
    }

    @DisplayName("예약자 이름이 공백인 경우, IllegalArgumentException이 발생한다.")
    @Test
    void 예약자_이름_공백_예외_테스트() {
        // given
        ReservationTime time = new ReservationTime(1L, LocalTime.of(10, 0));

        // when & then
        assertThatThrownBy(() -> new Reservation(1L, "   ", LocalDate.now(), time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 예약자의 이름은 비어 있거나, 공백일 수 없습니다.");
    }

    @DisplayName("예약 날짜가 null인 경우, IllegalArgumentException이 발생한다.")
    @Test
    void 예약_날짜_null_예외_테스트() {
        // given
        ReservationTime time = new ReservationTime(1L, LocalTime.of(10, 0));

        // when & then
        assertThatThrownBy(() -> new Reservation(1L, "brown", null, time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 예약 날짜는 비어 있을 수 없습니다.");
    }

    @DisplayName("예약 시간이 null인 경우, IllegalArgumentException이 발생한다.")
    @Test
    void 예약_시간_null_예외_테스트() {
        assertThatThrownBy(() -> new Reservation(1L, "brown", LocalDate.now(), null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 예약 시간은 비어 있을 수 없습니다.");
    }
}
