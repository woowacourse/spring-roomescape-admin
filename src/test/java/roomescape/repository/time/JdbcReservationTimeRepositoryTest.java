package roomescape.repository.time;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import roomescape.domain.ReservationTime;
import roomescape.repository.DatabaseCleanupListener;

@TestExecutionListeners(value = {
        DatabaseCleanupListener.class,
        DependencyInjectionTestExecutionListener.class
})
@JdbcTest
class JdbcReservationTimeRepositoryTest {

    @Autowired
    private DataSource dataSource;
    private JdbcReservationTimeRepository jdbcReservationTimeRepository;

    private final ReservationTime time1 = new ReservationTime(null, LocalTime.now());
    private final ReservationTime time2 = new ReservationTime(null, LocalTime.now());

    @BeforeEach
    void setUp() {
        jdbcReservationTimeRepository = new JdbcReservationTimeRepository(dataSource);
    }

    @DisplayName("저장된 모든 예약 시간 정보를 가져온다.")
    @Test
    void find_all_reservation_times() {
        jdbcReservationTimeRepository.insertReservationTime(time1);
        jdbcReservationTimeRepository.insertReservationTime(time2);

        List<ReservationTime> allReservationTimes = jdbcReservationTimeRepository.findAllReservationTimes();

        assertThat(allReservationTimes.size()).isEqualTo(2);
    }

    @DisplayName("예약 시간을 저장한다.")
    @Test
    void save_reservation_time() {
        ReservationTime time = jdbcReservationTimeRepository.insertReservationTime(time1);

        assertThat(time.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("예약 시간을 id로 삭제한다.")
    void delete_reservation_time_by_id() {
        jdbcReservationTimeRepository.insertReservationTime(time1);
        int beforeSize = jdbcReservationTimeRepository.findAllReservationTimes().size();

        jdbcReservationTimeRepository.deleteReservationTimeById(1L);
        int afterSize = jdbcReservationTimeRepository.findAllReservationTimes().size();

        assertAll(
                () -> assertThat(beforeSize).isEqualTo(1),
                () -> assertThat(afterSize).isEqualTo(0)
        );
    }
}
