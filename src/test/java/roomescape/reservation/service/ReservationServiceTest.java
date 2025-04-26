package roomescape.reservation.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.reservation.H2ReservationRepository;
import roomescape.reservation.reservation.ReservationRepository;
import roomescape.time.service.ReservationTimeService;
import roomescape.time.time.H2ReservationTimeRepository;
import roomescape.time.time.ReservationTimeRepository;


@JdbcTest
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationTimeService reservationTimeService;

    @Test
    void 예약_정보를_저장한다() {
        // given
        final LocalTime time = LocalTime.parse("10:00");
        final Long time_id = reservationTimeService.save(time);

        final String name = "헤일러";
        final LocalDate date = LocalDate.parse("2023-08-05");

        // when & then
        Assertions.assertThatCode(() -> reservationService.save(name, date, time_id))
                .doesNotThrowAnyException();
    }

    @Test
    void 예약_정보_목록을_조회한다() {
        // given
        final LocalTime time = LocalTime.parse("10:00");
        final LocalTime time2 = LocalTime.parse("18:00");

        final Long time_id1 = reservationTimeService.save(time);
        final Long time_id2 = reservationTimeService.save(time2);

        final String name = "헤일러";
        final LocalDate date = LocalDate.parse("2025-08-01");

        final String name2 = "머피";
        final LocalDate date2 = LocalDate.parse("2025-08-01");

        reservationService.save(name, date, time_id1);
        reservationService.save(name2, date2, time_id2);

        // when
        List<Reservation> reservations = reservationService.findAll();

        // then
        assertThat(reservations.size()).isEqualTo(2);
    }


    @TestConfiguration
    static class TestConfig {

        @Bean(name = "h2ReservationTimeRepository")
        public ReservationTimeRepository reservationTimeRepository(JdbcTemplate jdbcTemplate) {
            return new H2ReservationTimeRepository(jdbcTemplate);
        }

        @Bean
        public ReservationTimeService reservationTimeService(
                @Qualifier("h2ReservationTimeRepository") ReservationTimeRepository reservationTimeRepository
        ) {
            return new ReservationTimeService(reservationTimeRepository);
        }

        @Bean(name = "h2ReservationRepository")
        public ReservationRepository h2ReservationRepository(JdbcTemplate jdbcTemplate) {
            return new H2ReservationRepository(jdbcTemplate);
        }

        @Bean
        public ReservationService reservationService(
                @Qualifier("h2ReservationRepository") ReservationRepository reservationRepository,
                ReservationTimeService reservationTimeService
        ) {
            return new ReservationService(reservationRepository, reservationTimeService);
        }
    }
}
