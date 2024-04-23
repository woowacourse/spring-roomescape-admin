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
import roomescape.domain.Reservation;

import java.sql.PreparedStatement;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationDaoTest {

    @LocalServerPort
    int port;

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private final ReservationDao reservationDao;

    public @Autowired ReservationDaoTest(JdbcTemplate jdbcTemplate, ReservationDao reservationDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservationDao = reservationDao;
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void findAllTest() {
        List<Reservation> reservations = reservationDao.findAll();

        assertThat(reservations.size()).isEqualTo(1);
    }

    @Test
    void findById() {
        Reservation reservation = reservationDao.findById(1L);

        assertThat(reservation.getId()).isEqualTo(1L);
    }

    @Test
    void insertTest() {
        Long index = jdbcTemplate.queryForObject("SELECT count(*) FROM reservation", Long.class);
        Long id = reservationDao.insert("토미", "2024-01-02", 1L);

        assertThat(id).isEqualTo(index + 1);
    }

    @Test
    void deleteByIdTest() {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO reservation(name, date, time_Id) VALUES (?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, "네오");
            ps.setString(2, "2024-01-03");
            ps.setLong(3, 1L);
            return ps;
        }, keyHolder);

        Long key = keyHolder.getKey().longValue();
        reservationDao.deleteById(key);

        assertThat(reservationDao.findById(key)).isNull();
    }
}