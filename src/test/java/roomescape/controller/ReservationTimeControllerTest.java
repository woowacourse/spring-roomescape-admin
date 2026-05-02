package roomescape.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import roomescape.domain.ReservationTime;
import roomescape.service.ReservationTimeService;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FakeReservationTimeService extends ReservationTimeService {
    public FakeReservationTimeService() {
        super(null);
    }

    @Override
    public ReservationTime createReservationTime(ReservationTime reservationTime) {
        return new ReservationTime(999L, reservationTime.getStartAt());
    }

    @Override
    public List<ReservationTime> findAllReservationTime() {
        return List.of(new ReservationTime(999L, LocalTime.of(15, 0)));
    }

    @Override
    public void deleteReservationTime(Long id) {
    }

}

public class ReservationTimeControllerTest {
    private ReservationTimeController controller;
    private ResponseEntity<ReservationTime> createResponse;

    @BeforeEach
    void setUp() {
        ReservationTimeService fakeService = new FakeReservationTimeService();
        controller = new ReservationTimeController(fakeService);

        ReservationTime reservationTime = new ReservationTime(LocalTime.of(15, 0));

        createResponse = controller.create(reservationTime);
    }

    @Test
    @DisplayName("시간을 추가하면 200 코드를 반환한다.")
    void return200OK_When_AddReservationTime() {
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("시간을 조회하면 추가한 시간의 정보를 반환한다.")
    void returnReservationTimeInfo_When_AddReservationTime() {
        ResponseEntity<List<ReservationTime>> readResponse = controller.read();
        List<ReservationTime> reservationTimes = readResponse.getBody();

        assertThat(reservationTimes).isNotNull();
        assertThat(reservationTimes).hasSize(1);
        assertThat(reservationTimes.getFirst().getStartAt()).isEqualTo("15:00");
    }

    @Test
    @DisplayName("시간을 삭제하면 200 코드를 반환한다.")
    void return200OK_When_DeleteReservationTime() {
        Long fakeId = 1L;

        ResponseEntity<Void> deleteResponse = controller.delete(fakeId);

        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
