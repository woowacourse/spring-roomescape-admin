package roomescape.repository.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.time.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class H2ReservationTimeDaoTest {

    private static final LocalTime DEFAULT_TIME = LocalTime.of(15, 0);
    @Autowired
    private H2ReservationTimeDao h2ReservationTimeDao;
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    @Transactional
    void 예약_시간을_저장한다() {
        ReservationTime reservationTime = createReservationTimeStuff(DEFAULT_TIME);

        h2ReservationTimeDao.save(reservationTime);
        assertEquals(1, h2ReservationTimeDao.findAll().size());
    }

    @Test
    @Transactional
    void 모든_예약_시간을_조회한다() {
        save(createReservationTimeStuff(DEFAULT_TIME));
        save(createReservationTimeStuff(DEFAULT_TIME));
        save(createReservationTimeStuff(DEFAULT_TIME));

        assertEquals(3, h2ReservationTimeDao.findAll().size());
    }

    @Test
    @Transactional
    void 예약_시간을_삭제한다() {
        ReservationTime reservationTime = createReservationTimeStuff(DEFAULT_TIME);

        save(reservationTime);

        h2ReservationTimeDao.deleteById(reservationTime.getId());

        assertEquals(0, h2ReservationTimeDao.findAll().size());
    }

    ReservationTime createReservationTimeStuff(LocalTime startAt) {
        return new ReservationTime(startAt);
    }

    void save(final ReservationTime reservationTime) {
        JdbcTemplate jdbcTemplate = applicationContext.getBean(JdbcTemplate.class);
        String sql = "INSERT INTO reservation_time (start_at) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setTime(1, Time.valueOf(reservationTime.getStartAt()));
            return ps;
        }, keyHolder);

        reservationTime.setId(keyHolder.getKey().longValue());
    }
}
