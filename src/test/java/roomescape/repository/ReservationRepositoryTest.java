package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

class ReservationRepositoryTest {
    private ReservationRepository reservationRepository;
    private ReservationTimeRepository reservationTimeRepository;

    @BeforeEach
    void setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:reservation-test;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation");
        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation_time");

        jdbcTemplate.execute("""
                CREATE TABLE reservation_time (
                    id BIGINT NOT NULL AUTO_INCREMENT,
                    start_at VARCHAR(255) NOT NULL,
                    PRIMARY KEY (id)
                )
                """);

        jdbcTemplate.execute("""
                CREATE TABLE reservation (
                    id BIGINT NOT NULL AUTO_INCREMENT,
                    name VARCHAR(255) NOT NULL,
                    date VARCHAR(255) NOT NULL,
                    time_id BIGINT,
                    PRIMARY KEY (id),
                    FOREIGN KEY (time_id) REFERENCES reservation_time (id)
                )
                """);

        reservationRepository = new ReservationRepository(jdbcTemplate);
        reservationTimeRepository = new ReservationTimeRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("예약을 저장한다.")
    void save() {
        ReservationTime time = reservationTimeRepository.save("10:00");

        Reservation reservation = reservationRepository.save("브라운", "2026-04-29", time.id());

        assertThat(reservation.getId()).isEqualTo(1L);
        assertThat(reservation.getName()).isEqualTo("브라운");
        assertThat(reservation.getDate()).isEqualTo("2026-04-29");
        assertThat(reservation.getTime().id()).isEqualTo(1L);
        assertThat(reservation.getTime().startAt().toString()).isEqualTo("10:00");
    }

    @Test
    @DisplayName("저장된 에약을 조회한다.")
    void findAll() {
        ReservationTime firstTime = reservationTimeRepository.save("10:00");
        ReservationTime secondTime = reservationTimeRepository.save("11:00");

        reservationRepository.save("브라운", "2026-04-29", firstTime.id());
        reservationRepository.save("리사", "2026-04-30", secondTime.id());

        List<Reservation> reservations = reservationRepository.findAll();

        assertThat(reservations).hasSize(2);
        assertThat(reservations.get(0).getName()).isEqualTo("브라운");
        assertThat(reservations.get(0).getTime().startAt().toString()).isEqualTo("10:00");
        assertThat(reservations.get(1).getName()).isEqualTo("리사");
        assertThat(reservations.get(1).getTime().startAt().toString()).isEqualTo("11:00");
    }

    @Test
    @DisplayName("저장된 예약을 삭제한다.")
    void deleteById() {
        ReservationTime time = reservationTimeRepository.save("10:00");
        Reservation reservation = reservationRepository.save("브라운", "2026-04-29", time.id());

        reservationRepository.deleteById(reservation.getId());

        assertThat(reservationRepository.findAll()).isEmpty();
    }
}
