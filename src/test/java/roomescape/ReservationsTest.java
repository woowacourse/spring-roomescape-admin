package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.model.Reservation;
import roomescape.model.ReservationReqDto;
import roomescape.model.Reservations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ReservationsTest {

    @Test
    @DisplayName("새 예약 데이터를 추가하면 해당 데이터에 아이디를 부여하고 반환한다")
    void addAndGetTest() {
        // given
        Reservations reservations = new Reservations();
        ReservationReqDto reservationReqDto = new ReservationReqDto("포비", LocalDate.now(), LocalTime.now());

        // when
        Reservation newReservation = reservations.addAndGet(reservationReqDto);

        // then
        assertThat(newReservation.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("새 데이터 추가 시 생성된 id로 예약 데이터를 삭제할 수 있다")
    void deleteByIdTest() {
        // given
        Reservations reservations = new Reservations();
        ReservationReqDto reservationReqDto = new ReservationReqDto("포비", LocalDate.now(), LocalTime.now());

        // when
        Reservation newReservation = reservations.addAndGet(reservationReqDto);
        Long reservationId = newReservation.getId();

        // then
        assertDoesNotThrow(() -> reservations.deleteById(reservationId));
    }

    @Test
    @DisplayName("존재하지 않는 id의 데이터를 삭제하려고 하면 예외가 발생한다")
    void deleteByIdExceptionTest() {
        // given
        Reservations reservations = new Reservations();
        ReservationReqDto reservationReqDto = new ReservationReqDto("포비", LocalDate.now(), LocalTime.now());

        // when
        reservations.addAndGet(reservationReqDto);
        Long invalidId = 20L;

        // then
        assertThatThrownBy(() -> reservations.deleteById(invalidId))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
