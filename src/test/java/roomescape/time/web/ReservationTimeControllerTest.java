package roomescape.time.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTimeControllerTest {
    @DisplayName("방탈출_예약_시간을_추가하고_정보를_반환할_수_있다")
    @Test
    void create() {
        // given
        ReservationTimeController reservationTimeController = new ReservationTimeController(new FakeReservationTimeDao());
        LocalTime reservationTime = LocalTime.now();
        ReservationTimeRequest request = new ReservationTimeRequest(reservationTime);

        // when
        ReservationTimeResponse result = reservationTimeController.create(request);

        // then
        assertAll(
            () -> assertThat(result.id()).isEqualTo(1L),
            () -> assertThat(result.startAt()).isEqualTo(reservationTime)
        );
    }

    @DisplayName("모든_방탈출_예약_시간을_조회하고_반환할_수_있다")
    @Test
    void getAll() {
        // given
        ReservationTimeController reservationTimeController = new ReservationTimeController(new FakeReservationTimeDao());
        reservationTimeController.create(new ReservationTimeRequest(LocalTime.now()));

        // when
        List<ReservationTimeResponse> result = reservationTimeController.getAll();

        // then
        assertThat(result).hasSize(1);
    }

    @DisplayName("주어진_id의_예약_시간을_삭제할_수_있다")
    @Test
    void delete() {
        // given
        ReservationTimeController reservationTimeController = new ReservationTimeController(new FakeReservationTimeDao());
        reservationTimeController.create(new ReservationTimeRequest(LocalTime.now()));

        // when
        reservationTimeController.delete(1L);

        // then
        List<ReservationTimeResponse> reponses = reservationTimeController.getAll();
        assertThat(reponses).hasSize(0);
    }
}
