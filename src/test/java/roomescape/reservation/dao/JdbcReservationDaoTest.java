package roomescape.reservation.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import roomescape.reservation.Reservation;
import roomescape.reservation.ReservationTime;

class JdbcReservationDaoTest {
    private EmbeddedDatabase database;
    private JdbcReservationDao jdbcReservationDao;
    private ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(11, 20));

    @BeforeEach
    public void init() {
        database = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .generateUniqueName(true)
                .addScripts("schema.sql", "test-data.sql")
                .build();
        jdbcReservationDao = new JdbcReservationDao(new NamedParameterJdbcTemplate(database));
    }

    @AfterEach
    public void destroy() {
        database.shutdown();
    }

    @Test
    void save() {
        // given
        Reservation reservation = new Reservation(null, "ken", LocalDate.of(2025, 4, 28), reservationTime);

        // when
        Reservation result = jdbcReservationDao.save(reservation);

        // then
        List<Reservation> reservations = jdbcReservationDao.findAll();
        assertAll(
                () -> assertThat(result.getId()).isEqualTo(1L),
                () -> assertThat(result.getCustomerName()).isEqualTo(reservation.getCustomerName()),
                () -> assertThat(result.getReservationDate()).isEqualTo(reservation.getReservationDate()),
                () -> assertThat(result.getReservationTime()).isEqualTo(reservation.getReservationTime()),
                () -> assertThat(reservations).contains(result)
        );
    }

    @Test
    void findAll() {
        // given
        Reservation firstReservation = new Reservation(null, "ken", LocalDate.of(2025, 4, 28), reservationTime);
        Reservation secondReservation = new Reservation(null, "amy", LocalDate.of(2025, 4, 27), reservationTime);
        jdbcReservationDao.save(firstReservation);
        jdbcReservationDao.save(secondReservation);

        // when
        List<Reservation> result = jdbcReservationDao.findAll();

        // then
        assertThat(result).hasSize(2);
    }

    @Test
    void removeId() {
        // given
        Reservation reservation = new Reservation(null, "ken", LocalDate.of(2025, 4, 28), reservationTime);
        Reservation savedReservation = jdbcReservationDao.save(reservation);

        // when
        boolean result = jdbcReservationDao.removeById(savedReservation.getId());

        // then
        List<Reservation> reservations = jdbcReservationDao.findAll();
        assertAll(
                () -> assertThat(result).isTrue(),
                () -> assertThat(reservations).isEmpty()
        );
    }
}
