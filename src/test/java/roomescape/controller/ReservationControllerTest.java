package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import roomescape.dto.ReservationRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ReservationControllerTest {

    ReservationController controller;

    @BeforeEach
    void beforeEach() {
        controller = new ReservationController();
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
        controller.save(reservation);

        //when
        controller.delete(1L);

        //then
        assertThat(controller.getReservations()).isEmpty();

    }

    @DisplayName("존재하지 않는 예약 삭제시 404 반환")
    @Test
    void deleteNonExistentReservation() {
        final ResponseEntity<Void> response = controller.delete(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
