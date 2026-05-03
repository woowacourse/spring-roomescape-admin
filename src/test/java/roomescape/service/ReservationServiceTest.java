package roomescape.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.repository.FakeReservationDao;
import roomescape.repository.JdbcReservationRepository;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationServiceTest {

    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        FakeReservationDao fakeReservationDao = new FakeReservationDao();
        JdbcReservationRepository jdbcReservationRepository = new JdbcReservationRepository(fakeReservationDao);
        reservationService = new ReservationService(jdbcReservationRepository);
    }

    @Test
    @DisplayName("유효한 예약 정보를 통해 새로운 예약을 생성하고 상세 정보를 반환한다.")
    void saveReservation_ValidInformation_ReturnsJoinedDto() {
        LocalDate futureDate = LocalDate.now().plusDays(1);
        ReservationJoinedDto reservationJoinedDto = reservationService.saveReservation("브라운", futureDate, 1L);
        assertThat(reservationJoinedDto.name()).isEqualTo("브라운");
    }

    @Test
    @DisplayName("존재하는 예약을 식별자를 통해 삭제하면 전체 목록에서 사라진다.")
    void removeReservation_ExistingId_RemovesReservationFromStorage() {
        LocalDate futureDate = LocalDate.now().plusDays(1);
        ReservationJoinedDto reservationJoinedDto = reservationService.saveReservation("브라운", futureDate, 1L);
        reservationService.removeReservation(reservationJoinedDto.id());
        assertThat(reservationService.allReservations()).isEmpty();
    }
}
