package roomescape.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.service.ReservationService;
import roomescape.dto.ReservationRequestDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FakeReservationService extends ReservationService {

    public FakeReservationService() {
        super(null, null);
    }

    @Override
    public Reservation createReservation(ReservationRequestDTO requestDTO) {
        ReservationTime fakeTime = new ReservationTime(requestDTO.getTimeId(), LocalTime.of(15, 0));
        return new Reservation(999L, requestDTO.getName(), requestDTO.getDate(), fakeTime);
    }

    @Override
    public List<Reservation> findAllReservations() {
        ReservationTime fakeTime = new ReservationTime(LocalTime.of(15, 0));
        return List.of(new Reservation(999L, "user1", LocalDate.of(2026, 4, 29), fakeTime));
    }

    @Override
    public void deleteReservation(Long id) {
    }

}

public class ReservationControllerTest {
    private ReservationController controller;
    private ResponseEntity<Reservation> createResponse;

    @BeforeEach
    void setUp() {
        ReservationService fakeService = new FakeReservationService();
        controller = new ReservationController(fakeService);

        ReservationRequestDTO requestDTO = new ReservationRequestDTO("user1", LocalDate.of(2026, 4, 29), 1L);

        createResponse = controller.create(requestDTO);
    }

    @Test
    @DisplayName("예약자를 추가하면 200 코드를 반환한다.")
    void return200OK_When_AddReservation() {
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
        Long fakeId = 1L;

        ResponseEntity<Void> deleteResponse = controller.delete(fakeId);

        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
