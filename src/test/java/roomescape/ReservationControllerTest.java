package roomescape;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ReservationControllerTest {
    private ReservationController controller;
    private Reserver reserver;
    private ResponseEntity<Void> createResponse;

    @BeforeEach
    void setUp() {
        controller = new ReservationController();
        reserver = new Reserver("user1", LocalDate.of(2026, 4, 28),
                LocalDateTime.of(2026, 4, 28, 15, 0, 0));
        createResponse = controller.create(reserver);
    }

    @Test
    @DisplayName("예약자를 추가하면 201 코드를 반환한다.")
    void return201Created_When_AddReserver() {
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("예약자를 조회하면 추가한 예약자의 정보를 반환한다.")
    void returnReserverInfo_When_AddReserver() {
        ResponseEntity<List<Reserver>> readResponse = controller.read();
        List<Reserver> reservers = readResponse.getBody();

        assertThat(reservers).isNotNull();
        assertThat(reservers).hasSize(1);
        assertThat(reservers.getFirst().getName()).isEqualTo("user1");

    }
}
