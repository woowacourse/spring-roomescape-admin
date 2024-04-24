package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

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
import java.util.Optional;

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
        final ReservationTimeCreateRequest request = new ReservationTimeCreateRequest(LocalTime.parse("07:09"));
        final ReservationTime reservationTime = request.toReservationTime();

        final Long actual = reservationTimeRepository.save(reservationTime);

        assertThat(actual).isEqualTo(1L);
    }

    @Test
    @DisplayName("예약 시간 전체를 조회한다.")
    void findAllReservationTimes() {
        createInitialReservationTime();

        final List<ReservationTime> actual = reservationTimeRepository.findAll();

        assertThat(actual).hasSize(1);
    }

    @Test
    @DisplayName("id에 해당하는 예약 시간 정보를 조회한다.")
    void findReservationTimeById() {
        createInitialReservationTime();

        final ReservationTime actual = reservationTimeRepository.findById(1L).get();

        assertAll(
                () -> assertThat(actual.getId()).isEqualTo(1L),
                () -> assertThat(actual.getStartAt()).isEqualTo("15:40")
        );
    }

    @Test
    @DisplayName("id에 해당하는 예약 시간 정보가 없을 경우 빈 옵셔널을 반환한다.")
    void returnEmptyOptionalIfNotFindReservationTime() {
        final Optional<ReservationTime> actual = reservationTimeRepository.findById(2L);

        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("id에 해당하는 예약 시간 정보를 삭제한다.")
    void deleteReservationTimeById() {
        createInitialReservationTime();

        reservationTimeRepository.deleteById(1L);

        final Integer actual = jdbcTemplate.queryForObject("SELECT count(*) from reservation_time", Integer.class);
        assertThat(actual).isZero();
    }

    private void createInitialReservationTime() {
        jdbcTemplate.update("insert into reservation_time(start_at) values(?)", "15:40");
    }
}
