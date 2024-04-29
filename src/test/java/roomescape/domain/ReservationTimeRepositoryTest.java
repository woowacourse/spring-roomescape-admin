package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

/*
 * 테스트 데이터베이스 초기 데이터
 * {ID=1, START_AT=10:00}
 * {ID=2, START_AT=11:00}
 */
@JdbcTest
@Sql(scripts = "/reset_test_data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
class ReservationTimeRepositoryTest {
    private ReservationTimeRepository reservationTimeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        reservationTimeRepository = new JdbcReservationTimeRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("모든 예약 시간 데이터를 가져온다.")
    void findAll() {
        // when
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();

        // then
        assertThat(reservationTimes).hasSize(2);
    }

    @Test
    @DisplayName("특정 예약 시간 id의 데이터를 조회한다.")
    void findById() {
        // when
        ReservationTime findReservationTime = reservationTimeRepository.findById(2L).orElseThrow();

        // then
        assertThat(findReservationTime.getStartAt()).isEqualTo("11:00");
    }

    @Test
    @DisplayName("예약 시간을 생성한다.")
    void create() {
        // given
        LocalTime startAt = LocalTime.parse("13:00");
        ReservationTime inputData = new ReservationTime(null, startAt);

        // when
        ReservationTime createdTime = reservationTimeRepository.create(inputData);

        // then
        assertAll(
                () -> assertThat(createdTime.getStartAt()).isEqualTo(startAt),
                () -> assertThat(reservationTimeRepository.findAll()).hasSize(3)
        );
    }

    @Test
    @DisplayName("예약 시간을 삭제한다.")
    void delete() {
        // given
        Long id = 1L;

        // when
        reservationTimeRepository.removeById(id);

        // then
        assertAll(
                () -> assertThat(reservationTimeRepository.findById(id)).isEmpty(),
                () -> assertThat(reservationTimeRepository.findAll()).hasSize(1)
        );
    }
}
