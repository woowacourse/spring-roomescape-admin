package roomescape.domain.time.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.time.ReservationTime;

@JdbcTest
class JdbcReservationTimeRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ReservationTimeRepository reservationTimeRepository;

    @BeforeEach
    void setUp() {
        reservationTimeRepository = new JdbcReservationTimeRepository(jdbcTemplate);
    }

    @Test
    void 예약_시간을_저장한다() {
        LocalTime startAt = LocalTime.of(13, 0);
        ReservationTime reservationTime = new ReservationTime(startAt);

        reservationTime = reservationTimeRepository.save(reservationTime);

        ReservationTime savedReservationTime = reservationTimeRepository.findById(reservationTime.getId()).get();
        assertThat(savedReservationTime.getStartAt()).isEqualTo(startAt);
    }

    @Test
    void 모든_예약_시간을_조회한다() {
        ReservationTime reservationTime1 = new ReservationTime(LocalTime.of(13, 0));
        ReservationTime reservationTime2 = new ReservationTime(LocalTime.of(14, 0));
        reservationTimeRepository.save(reservationTime1);
        reservationTimeRepository.save(reservationTime2);

        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();
        assertAll(
                () -> assertThat(reservationTimes).hasSize(2),
                () -> assertThat(reservationTimes.get(0).getStartAt()).isEqualTo(LocalTime.of(13, 0)),
                () -> assertThat(reservationTimes.get(1).getStartAt()).isEqualTo(LocalTime.of(14, 0))
        );
    }

    @Test
    void 예약_시간을_삭제한다() {
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(13, 0));
        reservationTime = reservationTimeRepository.save(reservationTime);

        reservationTimeRepository.deleteById(reservationTime.getId());

        assertThat(reservationTimeRepository.findAll()).isEmpty();
    }
}
