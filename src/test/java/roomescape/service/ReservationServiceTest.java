package roomescape.service;

import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import roomescape.data.dto.request.ReservationRequest;
import roomescape.fixture.FakeReservationRepository;
import roomescape.fixture.FakeReservationTimeRepository;

@SpringBootTest
class ReservationServiceTest {

    private ReservationService reservationService = new ReservationService(new FakeReservationRepository());

    @Test
    @DisplayName("모든 예약 정보를 가져온다.")
    void getAllReservationQuery_ShouldBringAllData_WhenDBHasReservationInfo() {
        final var reservationList = reservationService.getAllReservationQuery();
        
        Assertions.assertThat(reservationList).isEmpty();
    }
    
    @Test
    @DisplayName("예약 정보를 저장한다")
    void createReservationCommand_ShouldSaveDate_WhenHasReservationInfo() {
        final var request = new ReservationRequest("dummy", LocalDate.now(), 1);
        reservationService.createReservationCommand(request);

        Assertions.assertThat(reservationService.getAllReservationQuery()).hasSize(1);
    }
}
