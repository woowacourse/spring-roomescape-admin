package roomescape.time.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ReservationTimeControllerTest {
    @DisplayName("방탈출_예약_시간을_추가하고_정보를_반환할_수_있다")
    @Test
    void create() {
        // given
        ReservationTimeController reservationTimeController = new ReservationTimeController(
                new FakeReservationTimeDao());
        LocalTime reservationTime = LocalTime.now();
        ReservationTimeRequest request = new ReservationTimeRequest(reservationTime);

        // when
        ResponseEntity<ReservationTimeResponse> result = reservationTimeController.create(request);

        // then
        assertAll(
                () -> assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(result.getBody().id()).isEqualTo(1L),
                () -> assertThat(result.getBody().startAt()).isEqualTo(reservationTime)
        );
    }

    @DisplayName("모든_방탈출_예약_시간을_조회하고_반환할_수_있다")
    @Test
    void getAll() {
        // given
        ReservationTimeController reservationTimeController = new ReservationTimeController(
                new FakeReservationTimeDao());
        reservationTimeController.create(new ReservationTimeRequest(LocalTime.now()));

        // when
        ResponseEntity<List<ReservationTimeResponse>> result = reservationTimeController.getAll();

        // then
        assertAll(
                () -> assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(result.getBody()).hasSize(1)
        );
    }

    @DisplayName("주어진_id의_예약_시간을_삭제할_수_있다")
    @Test
    void delete() {
        // given
        ReservationTimeController reservationTimeController = new ReservationTimeController(
                new FakeReservationTimeDao());
        reservationTimeController.create(new ReservationTimeRequest(LocalTime.now()));

        // when
        ResponseEntity<Void> result = reservationTimeController.delete(1L);

        // then
        List<ReservationTimeResponse> responses = reservationTimeController.getAll().getBody();
        assertAll(
                () -> assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(responses).hasSize(0)
        );
    }

    @DisplayName("존재하지_않는_id의_예약_시간을_삭제하려고_하면_404를_응답한다")
    @Test
    void delete_WhenResourceNotExists() {
        // given
        ReservationTimeController reservationTimeController = new ReservationTimeController(
                new FakeReservationTimeDao());

        // when
        ResponseEntity<Void> result = reservationTimeController.delete(1L);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
