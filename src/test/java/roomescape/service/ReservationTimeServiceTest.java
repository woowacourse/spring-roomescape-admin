package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.ReservationTime;
import roomescape.repository.FakeReservationTimeDao;
import roomescape.repository.JdbcReservationTimeRepository;

class ReservationTimeServiceTest {

    private ReservationTimeService reservationTimeService;

    @BeforeEach
    void setUp() {
        FakeReservationTimeDao fakeReservationTimeDao = new FakeReservationTimeDao();
        JdbcReservationTimeRepository jdbcReservationTimeRepository = new JdbcReservationTimeRepository(
                fakeReservationTimeDao);
        reservationTimeService = new ReservationTimeService(jdbcReservationTimeRepository);
    }

    @Test
    @DisplayName("유효한 시간 정보를 통해 새로운 예약 시간을 생성하고 반환한다.")
    void saveTime_ValidTime_ReturnsSavedReservationTime() {
        ReservationTime reservationTime = reservationTimeService.saveTime(LocalTime.of(10, 0));
        assertThat(reservationTime.startAt()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    @DisplayName("존재하는 예약 시간을 식별자를 통해 삭제하면 전체 목록에서 사라진다.")
    void removeTime_ExistingId_RemovesTimeFromStorage() {
        ReservationTime reservationTime = reservationTimeService.saveTime(LocalTime.of(10, 0));
        reservationTimeService.removeTime(reservationTime.id());
        assertThat(reservationTimeService.allTimes()).isEmpty();
    }
}
