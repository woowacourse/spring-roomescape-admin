package roomescape.dao;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.ReservationTime;

import java.sql.PreparedStatement;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationTimeDaoTest {

    @LocalServerPort
    int port;

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private final ReservationTimeDao reservationTimeDao;

    public @Autowired ReservationTimeDaoTest(JdbcTemplate jdbcTemplate, ReservationTimeDao reservationTimeDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservationTimeDao = reservationTimeDao;
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void findAllTest() {
        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();

        assertThat(reservationTimes.size()).isEqualTo(1);
    }

    @Test
    void insertTest() {
        Long index = jdbcTemplate.queryForObject("SELECT count(*) FROM reservation_time", Long.class);
        Long id = reservationTimeDao.insert("01:01").get();

        assertThat(id).isEqualTo(index + 1);
    }

    @Test
    void deleteByIdTest() {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO reservation_time(start_at) VALUES ?",
                    new String[]{"id"});
            ps.setString(1, "01:02");
            return ps;
        }, keyHolder);

        Long key = keyHolder.getKey().longValue();
        reservationTimeDao.deleteById(key);

        assertThat(reservationTimeDao.findAll().stream().map(ReservationTime::getId).toList()).doesNotContain(key);
    }
}
