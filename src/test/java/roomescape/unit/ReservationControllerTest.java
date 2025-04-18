package roomescape.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.HttpStatus.OK;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import roomescape.ReservationController;
import roomescape.ReservationRequest;
import roomescape.ReservationResponse;

class ReservationControllerTest {
    @DisplayName("예약을_생성할_수_있다")
    @Test
    void create() {
        // given
        ReservationController reservationController = new ReservationController();
        String name = "레오";
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        ReservationRequest request = new ReservationRequest(name, date, time);

        // when
        ResponseEntity<ReservationResponse> result = reservationController.create(request);
        ReservationResponse response = result.getBody();

        // then
        assertAll(
                () -> assertThat(result.getStatusCode()).isEqualTo(OK),
                () -> assertThat(response.id()).isOne(),
                () -> assertThat(response.name()).isEqualTo(name),
                () -> assertThat(response.date()).isEqualTo(date),
                () -> assertThat(response.time()).isEqualTo(time)
        );
    }

    @DisplayName("모든_예약_정보를_반환할_수_있다")
    @Test
    void getAll() {
        // given
        ReservationController reservationController = new ReservationController();
        ReservationRequest request = new ReservationRequest("레오", LocalDate.now(), LocalTime.now());
        reservationController.create(request);

        // when
        ResponseEntity<List<ReservationResponse>> result = reservationController.getAll();
        List<ReservationResponse> responses = result.getBody();

        // then
        assertAll(
                () -> assertThat(result.getStatusCode()).isEqualTo(OK),
                () -> assertThat(responses).hasSize(1)
        );
    }

    @DisplayName("주어진_id의_예약을_삭제할_수_있다")
    @Test
    void delete() {
        // given
        ReservationController reservationController = new ReservationController();
        ReservationRequest request = new ReservationRequest("레오", LocalDate.now(), LocalTime.now());
        ResponseEntity<ReservationResponse> responseEntity = reservationController.create(request);

        // when
        reservationController.delete(responseEntity.getBody().id());

        // then
        List<ReservationResponse> responses = reservationController.getAll().getBody();
        assertThat(responses).hasSize(0);
    }
}
