package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDate;
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
                    PRIMARY KEY (id),
                    UNIQUE (start_at)
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
        ReservationTime time = saveTime(LocalTime.of(10, 0));

        Reservation reservation = saveReservation("브라운", LocalDate.of(2026, 4, 29), time);

        assertThat(reservation.getId()).isEqualTo(1L);
        assertThat(reservation.getName()).isEqualTo("브라운");
        assertThat(reservation.getDate()).isEqualTo("2026-04-29");
        assertThat(reservation.getTime().id()).isEqualTo(1L);
        assertThat(reservation.getTime().startAt().toString()).isEqualTo("10:00");
    }

    @Test
    @DisplayName("저장된 에약을 조회한다.")
    void findAll() {
        ReservationTime firstTime = saveTime(LocalTime.of(10, 0));
        ReservationTime secondTime = saveTime(LocalTime.of(11, 0));

        saveReservation("브라운", LocalDate.of(2026, 4, 29), firstTime);
        saveReservation("리사", LocalDate.of(2026, 4, 30), secondTime);

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
        ReservationTime time = saveTime(LocalTime.of(10, 0));
        Reservation reservation = saveReservation("브라운", LocalDate.of(2026, 4, 29), time);

        reservationRepository.deleteById(reservation.getId());

        assertThat(reservationRepository.findAll()).isEmpty();
    }

    private ReservationTime saveTime(LocalTime startAt) {
        return reservationTimeRepository.save(new ReservationTime(null, startAt));
    }

    private Reservation saveReservation(String name, LocalDate date, ReservationTime time) {
        Reservation reservation = new Reservation(
                null,
                new Name(name),
                new ReservationDate(date),
                time
        );
        return reservationRepository.save(reservation);
    }
}
