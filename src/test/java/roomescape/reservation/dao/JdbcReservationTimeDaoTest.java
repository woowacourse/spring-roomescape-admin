package roomescape.reservation.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import roomescape.reservation.ReservationTime;

class JdbcReservationTimeDaoTest {
    private EmbeddedDatabase database;
    private JdbcReservationTimeDao jdbcReservationTimeDao;

    @BeforeEach
    public void init() {
        database = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .generateUniqueName(true)
                .addScript("schema.sql")
                .build();
        jdbcReservationTimeDao = new JdbcReservationTimeDao(new NamedParameterJdbcTemplate(database));
    }

    @AfterEach
    public void destroy() {
        database.shutdown();
    }

    @Test
    void save() {
        // given
        LocalTime time = LocalTime.of(11, 20);
        ReservationTime reservationTime = new ReservationTime(null, time);

        // when
        ReservationTime result = jdbcReservationTimeDao.save(reservationTime);
        List<ReservationTime> reservationTimes = jdbcReservationTimeDao.findAll();

        // then
        assertAll(
                () -> assertThat(result.getId()).isEqualTo(1L),
                () -> assertThat(result.getStartAt()).isEqualTo(time),
                () -> assertThat(reservationTimes).contains(result),
                () -> assertThat(reservationTimes).hasSize(1)
        );
    }

    @Test
    void findAll() {
        // given
        ReservationTime firstReservationTime = new ReservationTime(null, LocalTime.of(11, 20));
        ReservationTime secondReservationTime = new ReservationTime(null, LocalTime.of(12, 20));
        jdbcReservationTimeDao.save(firstReservationTime);
        jdbcReservationTimeDao.save(secondReservationTime);

        // when
        List<ReservationTime> result = jdbcReservationTimeDao.findAll();

        // then
        assertThat(result).hasSize(2);
    }

    @Test
    void removeById() {
        // given
        ReservationTime reservationTime = new ReservationTime(null, LocalTime.of(11, 20));
        ReservationTime savedReservationTime = jdbcReservationTimeDao.save(reservationTime);

        // when
        boolean result = jdbcReservationTimeDao.removeById(savedReservationTime.getId());

        // then
        List<ReservationTime> reservationTimes = jdbcReservationTimeDao.findAll();
        assertAll(
                () -> assertThat(result).isTrue(),
                () -> assertThat(reservationTimes).isEmpty()
        );
    }

    @Test
    void getById() {
        // given
        ReservationTime reservationTime = new ReservationTime(null, LocalTime.of(11, 20));
        ReservationTime savedReservationTime = jdbcReservationTimeDao.save(reservationTime);

        // when
        ReservationTime result = jdbcReservationTimeDao.getById(savedReservationTime.getId());

        // then
        assertThat(result).isEqualTo(savedReservationTime);
    }
}
