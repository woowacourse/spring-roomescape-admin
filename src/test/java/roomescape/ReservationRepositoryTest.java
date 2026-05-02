package roomescape;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.entity.Reservation;
import roomescape.domain.entity.ReservationTime;
import roomescape.domain.repository.ReservationRepository;
import roomescape.domain.repository.ReservationTimeRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @Test
    void 예약을_생성하면_DB에_정상적으로_저장된다() {
        Long timeId = reservationTimeRepository.save(ReservationTime.create(null, LocalTime.of(10, 0)));

        Reservation reservation = Reservation.create(
                null,
                "바니",
                LocalDate.of(2026, 5, 1),
                ReservationTime.create(timeId, LocalTime.of(10, 0)));

        Long id = reservationRepository.save(reservation);

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM reservation WHERE id = ?",
                Integer.class,
                id
        );

        assertThat(count).isEqualTo(1);
    }

    @Test
    void 예약을_조회하면_전체_예약_목록이_반환된다() {
        Long timeId = reservationTimeRepository.save(ReservationTime.create(null, LocalTime.of(10, 0)));

        Reservation reservation = Reservation.create(
                null,
                "바니",
                LocalDate.of(2026, 5, 1),
                ReservationTime.create(timeId, LocalTime.of(10, 0)));
        Long id = reservationRepository.save(reservation);

        Reservation savedReservation = Reservation.create(
                id,
                "바니",
                LocalDate.of(2026, 5, 1),
                ReservationTime.create(timeId, LocalTime.of(10, 0)));

        List<Reservation> result = reservationRepository.findAll();

        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void 특정_시간대의_예약을_모두_삭제한다() {
        Long timeId = reservationTimeRepository.save(ReservationTime.create(null, LocalTime.of(10, 0)));

        Reservation reservation = Reservation.create(
                null,
                "바니",
                LocalDate.of(2026, 5, 1),
                ReservationTime.create(timeId, LocalTime.of(10, 0)));
        reservationRepository.save(reservation);

        reservationRepository.deleteAllByTimeId(timeId);

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM reservation WHERE time_id = ?",
                Integer.class,
                timeId
        );

        assertThat(count).isEqualTo(0);
    }

    @Test
    void 예약을_삭제하면_DB에서_삭제된다() {
        Long timeId = reservationTimeRepository.save(ReservationTime.create(null, LocalTime.of(10, 0)));

        Reservation reservation = Reservation.create(
                null,
                "바니",
                LocalDate.of(2026, 5, 1),
                ReservationTime.create(timeId, LocalTime.of(10, 0)));
        Long id = reservationRepository.save(reservation);

        reservationRepository.deleteById(id);

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM reservation WHERE id = ?",
                Integer.class,
                id
        );

        assertThat(count).isEqualTo(0);
    }

    @Test
    void 존재하지_않는_예약을_삭제하면_예외를_던진다() {
        assertThatThrownBy(() -> reservationRepository.deleteById(1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 특정_날짜와_시간에_예약이_존재하면_true를_반환한다() {
        Long timeId = reservationTimeRepository.save(ReservationTime.create(null, LocalTime.of(10, 0)));

        Reservation reservation = Reservation.create(
                null,
                "바니",
                LocalDate.of(2026, 5, 1),
                ReservationTime.create(timeId, LocalTime.of(10, 0)));
        reservationRepository.save(reservation);

        boolean result = reservationRepository.existsByDateAndTime(
                LocalDate.of(2026, 5, 1),
                ReservationTime.create(timeId, LocalTime.of(10, 0))
        );

        assertThat(result).isTrue();
    }
}
