package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.controller.dto.ReservationTimeCreateRequest;
import roomescape.domain.ReservationTime;

import java.time.LocalTime;
import java.util.List;


@JdbcTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeJdbcRepositoryTest {

    private final ReservationTimeRepository reservationTimeRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationTimeJdbcRepositoryTest(final JdbcTemplate jdbcTemplate) {
        this.reservationTimeRepository = new ReservationTimeJdbcRepository(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Test
    @DisplayName("예약 시간을 저장한다.")
    void saveReservationTime() {
        final ReservationTimeCreateRequest createRequest = new ReservationTimeCreateRequest(LocalTime.parse("07:09"));

        final Long id = reservationTimeRepository.save(createRequest);

        assertThat(id).isEqualTo(1L);
    }

    @Test
    @DisplayName("예약 시간 전체를 조회한다.")
    void findAllReservationTimes() {
        jdbcTemplate.update("insert into reservation_time(start_at) values(?)", "15:40");

        final List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();

        assertThat(reservationTimes).hasSize(1);
    }

    @Test
    @DisplayName("id에 해당하는 예약 시간 정보를 삭제한다.")
    void deleteReservationTimeById() {
        jdbcTemplate.update("insert into reservation_time(start_at) values(?)", "15:40");

        reservationTimeRepository.deleteById(1L);

        final Integer count = jdbcTemplate.queryForObject("SELECT count(*) from reservation_time", Integer.class);
        assertThat(count).isZero();
    }
}
