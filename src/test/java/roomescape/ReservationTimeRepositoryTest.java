package roomescape;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.entity.ReservationTime;
import roomescape.domain.repository.ReservationTimeRepository;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationTimeRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @Test
    void 예약_시간대를_생성하면_DB에_정상적으로_저장된다() {
        Long id = reservationTimeRepository.save(ReservationTime.create(null, LocalTime.of(10, 0)));
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM reservation_time WHERE id = ?",
                Integer.class,
                id
        );

        assertThat(count).isEqualTo(1);
    }

    @Test
    void 시간대_목록을_조회하면_전체_시간_목록이_반환된다() {
        reservationTimeRepository.save(ReservationTime.create(null, LocalTime.of(10, 0)));
        reservationTimeRepository.save(ReservationTime.create(null, LocalTime.of(10, 1)));

        assertThat(reservationTimeRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    void 특정_예약_시간대를_조회한다() {
        reservationTimeRepository.save(ReservationTime.create(null, LocalTime.of(10, 0)));

    }

    @Test
    void 존재하지_않는_예약_시간대를_조회하면_예외를_던진다() {
        assertThatThrownBy(() -> reservationTimeRepository.getById(1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 예약_시간대를_삭제하면_DB에서_삭제된다() {
        Long id = reservationTimeRepository.save(ReservationTime.create(null, LocalTime.of(10, 0)));

        reservationTimeRepository.deleteById(id);

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM reservation_time WHERE id = ?",
                Integer.class,
                id
        );

        assertThat(count).isEqualTo(0);
    }

    @Test
    void 존재하지_않는_예약_시간대를_삭제하면_예외를_던진다() {
        assertThatThrownBy(() -> reservationTimeRepository.deleteById(1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 특정_시간대가_존재하면_true를_반환한다() {
        reservationTimeRepository.save(ReservationTime.create(null, LocalTime.of(10, 0)));

        boolean result = reservationTimeRepository.existsByTime(LocalTime.of(10, 0));

        assertThat(result).isTrue();
    }

}
