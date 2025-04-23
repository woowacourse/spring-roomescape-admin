package roomescape.repository;

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
import roomescape.domain.ReservationTime;


@JdbcTest
class ReservationTimeJdbcRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ReservationTimeJdbcRepository reservationTimeJdbcRepository;

    @BeforeEach
    void setUp() {
        reservationTimeJdbcRepository = new ReservationTimeJdbcRepository(jdbcTemplate);
    }

    @DisplayName("예약 시간을 성공적으로 저장한다.")
    @Test
    void saveTest() {
        //given
        final ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(12, 0));

        //when
        final ReservationTime expected = reservationTimeJdbcRepository.save(reservationTime);

        //then
        assertAll(
                () -> assertThat(expected.getId()).isEqualTo(reservationTime.getId()),
                () -> assertThat(expected.getStartAt()).isEqualTo(reservationTime.getStartAt())
        );
    }

    @DisplayName("예약 시간들을 성공적으로 조회한다.")
    @Test
    void findByIdTest() {
        //given
        final ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(12, 0));
        reservationTimeJdbcRepository.save(reservationTime);

        //when
        final List<ReservationTime> expected = reservationTimeJdbcRepository.findAll();

        //then
        assertThat(expected).hasSize(1);
    }

    @DisplayName("예약 시간을 성공적으로 삭제한다.")
    @Test
    void deleteByIdTest() {
        //given
        final ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(12, 0));
        reservationTimeJdbcRepository.save(reservationTime);

        //when
        final int expected = reservationTimeJdbcRepository.deleteById(reservationTime.getId());

        //then
        assertThat(expected).isPositive();
    }
}
