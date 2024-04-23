package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerTest {

    @Autowired
    ReservationController controller;

    @Test
    @DisplayName("예약 조회")
    void getReservation() {
        assertThat(controller.getReservations()).isEmpty();
    }

    @DisplayName("예약 추가")
    @Test
    void saveReservation() {
        //given
        final ReservationRequest reservation = new ReservationRequest("레디", "2024-04-17", "13:00");

        //when
        controller.save(reservation);

        //then
        assertThat(controller.getReservations()).hasSize(1);
    }


    @DisplayName("예약 삭제")
    @Test
    void deleteReservation() {
        //given
        final ReservationRequest reservation = new ReservationRequest("레디", "2024-04-17", "13:00");
        final ResponseEntity<ReservationResponse> saved = controller.save(reservation);
        final ReservationResponse body = saved.getBody();

        //when
        assert body != null;
        controller.delete(body.id());

        //then
        assertThat(controller.getReservations()).isEmpty();

    }

    @DisplayName("존재하지 않는 예약 삭제시 404 반환")
    @Test
    void deleteNonExistentReservation() {
        final ResponseEntity<Void> response = controller.delete(100L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
