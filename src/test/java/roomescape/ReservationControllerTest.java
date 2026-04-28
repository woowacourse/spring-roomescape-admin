package roomescape;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ReservationControllerTest {
    private ReservationController controller;

    @BeforeEach
    void setUp() {
        controller = new ReservationController();
    }

    @Test
    @DisplayName("예약자를 추가하면 201 코드를 반환한다.")
    void return201Created_When_AddReserver() {
        Reserver reserver = new Reserver("user1", LocalDate.of(2026, 4, 28),
                LocalDateTime.of(2026, 4, 28, 15, 0, 0));
        ResponseEntity<Void> createResponse = controller.create(reserver);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}
