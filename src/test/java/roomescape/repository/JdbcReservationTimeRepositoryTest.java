package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.ReservationTime;

class JdbcReservationTimeRepositoryTest {

    private JdbcReservationTimeRepository jdbcReservationTimeRepository;

    @BeforeEach
    void setUp() {
        FakeReservationTimeDao fakeReservationTimeDao = new FakeReservationTimeDao();
        jdbcReservationTimeRepository = new JdbcReservationTimeRepository(fakeReservationTimeDao);
    }

    @Test
    @DisplayName("새로운 예약 시간을 저장하고 부여된 식별자로 다시 조회할 수 있다.")
    void save_ValidTime_CanBeFoundById() {
        long timeId = jdbcReservationTimeRepository.save(LocalTime.of(10, 0));
        ReservationTime reservationTime = jdbcReservationTimeRepository.findById(timeId);
        assertThat(reservationTime.startAt()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    @DisplayName("존재하는 예약 시간을 식별자를 통해 성공적으로 삭제할 수 있다.")
    void deleteById_ExistingId_RemovesReservationTime() {
        long timeId = jdbcReservationTimeRepository.save(LocalTime.of(10, 0));
        jdbcReservationTimeRepository.deleteById(timeId);
        assertThat(jdbcReservationTimeRepository.findAll()).isEmpty();
    }
}
