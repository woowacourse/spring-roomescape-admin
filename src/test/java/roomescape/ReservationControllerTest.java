package roomescape;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ReservationControllerTest {
    private ReservationController controller;
    private Reservation reservation;
    private ResponseEntity<Reservation> createResponse;

    @BeforeEach
    void setUp() {
        controller = new ReservationController();
        reservation = new Reservation("user1", LocalDate.of(2026, 4, 28),
                LocalTime.of(15, 0, 0));
        createResponse = controller.create(reservation);
    }

    @Test
    @DisplayName("예약자를 추가하면 200 코드를 반환한다.")
    void return201Created_When_AddReservation() {
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("예약자를 조회하면 추가한 예약자의 정보를 반환한다.")
    void returnReservationInfo_When_AddReservation() {
        ResponseEntity<List<Reservation>> readResponse = controller.read();
        List<Reservation> reservations = readResponse.getBody();

        assertThat(reservations).isNotNull();
        assertThat(reservations).hasSize(1);
        assertThat(reservations.getFirst().getName()).isEqualTo("user1");
    }

    @Test
    @DisplayName("예약자를 삭제하면 200 코드를 반환한다.")
    void return200OK_When_DeleteReservation() {
        ResponseEntity<List<Reservation>> readResponse = controller.read();
        List<Reservation> reservations = readResponse.getBody();
        Long id = reservations.getFirst().getId();
        int beforeSize = reservations.size();

        ResponseEntity<List<Reservation>> deleteResponse = controller.delete(id);
        int afterSize = reservations.size();

        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(beforeSize).isEqualTo(afterSize + 1);
    }
}
