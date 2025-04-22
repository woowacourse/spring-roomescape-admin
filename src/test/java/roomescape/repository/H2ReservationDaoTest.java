package roomescape.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class H2ReservationDaoTest {

    @Autowired
    private H2ReservationDao h2ReservationDao;
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    @Transactional
    void 예약을_저장한다() {
        Reservation reservation = createReservationStuff("r1");

        h2ReservationDao.save(reservation);
        assertEquals(1, h2ReservationDao.findAll().size());
    }

    @Test
    @Transactional
    void 식별자를_통해_예약을_조회한다() {
        Reservation reservation = createReservationStuff("r1");
        save(reservation);

        assertEquals(h2ReservationDao.findById(reservation.getId()).get(), reservation);
    }

    @Test
    @Transactional
    void 모든_예약을_조회한다() {
        save(createReservationStuff("r1"));
        save(createReservationStuff("r2"));
        save(createReservationStuff("r3"));

        assertEquals(3, h2ReservationDao.findAll().size());
    }

    @Test
    @Transactional
    void 예약을_삭제한다() {
        Reservation reservation = createReservationStuff("r1");

        save(reservation);

        h2ReservationDao.deleteById(1);

        assertEquals(0, h2ReservationDao.findAll().size());
    }

    Reservation createReservationStuff(String name) {
        return new Reservation(name, LocalDate.now(), LocalTime.now().withNano(0));
    }

    void save(final Reservation reservation) {
        JdbcTemplate jdbcTemplate = applicationContext.getBean(JdbcTemplate.class);
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setDate(2, Date.valueOf(reservation.getDate()));
            ps.setTime(3, Time.valueOf(reservation.getTime()));
            return ps;
        }, keyHolder);

        reservation.setId(keyHolder.getKey().longValue());
    }
}
