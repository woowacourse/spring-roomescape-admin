package roomescape.repository;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.ReservationTime;
import roomescape.exception.DomainException;
import roomescape.exception.ErrorCode;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationTimeRepositoryTest {

    private ReservationTimeRepository reservationTimeRepository;

    @BeforeEach
    void setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:test;MODE=MySQL;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("test");
        dataSource.setPassword("");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation");
        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation_time");

        jdbcTemplate.execute("""
            CREATE TABLE reservation_time (
                id       BIGINT       NOT NULL AUTO_INCREMENT,
                start_at VARCHAR(255) NOT NULL,
                PRIMARY KEY (id)
            )
            """);

        reservationTimeRepository = new JdbcReservationTimeRepository(jdbcTemplate);
    }


    @Test
    void 예약_시간을_저장하고_조회한다() {
        ReservationTime reservationTime = reservationTimeRepository.save(new ReservationTime(LocalTime.of(10, 0)));

        ReservationTime found = reservationTimeRepository.findById(reservationTime.getId());

        assertThat(found.getId()).isEqualTo(reservationTime.getId());
        assertThat(found.getStartAt()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    void 예약_시간_목록을_조회한다() {
        reservationTimeRepository.save(new ReservationTime(LocalTime.of(10, 0)));

        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();

        assertThat(reservationTimes).hasSize(1);
        assertThat(reservationTimes.getFirst().getStartAt()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    void 예약_시간을_삭제한다() {
        ReservationTime reservationTime = reservationTimeRepository.save(new ReservationTime(LocalTime.of(10, 0)));

        reservationTimeRepository.deleteById(reservationTime.getId());

        assertThat(reservationTimeRepository.findAll()).isEmpty();
    }

    @Test
    void 존재하지_않는_예약_시간을_조회하면_예외가_발생한다() {
        assertThatThrownBy(() -> reservationTimeRepository.findById(1L))
                .isInstanceOf(DomainException.class)
                .hasMessage(ErrorCode.RESERVATION_TIME_NOT_FOUND.message());
    }

    @Test
    void 존재하지_않는_예약_시간을_삭제하면_예외가_발생한다() {
        assertThatThrownBy(() -> reservationTimeRepository.deleteById(1L))
                .isInstanceOf(DomainException.class)
                .hasMessage(ErrorCode.RESERVATION_TIME_NOT_FOUND.message());
    }
}
