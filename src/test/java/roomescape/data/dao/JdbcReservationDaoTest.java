package roomescape.data.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.business.domain.Reservation;
import roomescape.business.domain.Time;
import roomescape.data.entity.ReservationEntity;
import roomescape.data.entity.TimeEntity;

@JdbcTest
class JdbcReservationDaoTest {

    private ReservationDao reservationDao;

    private final JdbcTemplate jdbcTemplate;
    private final Time timeFixture = new Time(1L, LocalTime.of(10, 10));

    @Autowired
    public JdbcReservationDaoTest(final JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS reservation_time
                (
                    id SERIAL,
                    start_at VARCHAR(255) NOT NULL,
                    PRIMARY KEY (id)
                );
                """);
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES ('10:10')");

        this.jdbcTemplate = jdbcTemplate;
    }

    @BeforeEach
    void setUp() {
        reservationDao = new JdbcReservationDao(jdbcTemplate);

        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation CASCADE");
        jdbcTemplate.execute("""
                CREATE TABLE reservation
                (
                    id SERIAL,
                    name VARCHAR(255) NOT NULL,
                    date VARCHAR(255) NOT NULL,
                    time_id BIGINT,
                    PRIMARY KEY (id),
                    FOREIGN KEY (time_id) REFERENCES reservation_time (id)
                );
                """);
    }

    @DisplayName("데이터베이스에 방탈출 예약을 저장한다.")
    @Test
    void save() {
        // given & when
        final Long id = reservationDao.save(new Reservation(
                "hotteok",
                LocalDate.of(2025, 1, 1),
                timeFixture
        ));
        final ReservationEntity actual = jdbcTemplate.queryForObject("""
                SELECT\s
                    r.id as reservation_id,\s
                    r.name,\s
                    r.date,\s
                    t.id as time_id,\s
                    t.start_at as time_value\s
                    FROM reservation as r\s
                    inner join reservation_time as t\s
                    on r.time_id = t.id
                    WHERE r.id = ?
                """, ReservationEntity.getDefaultRowMapper(), id
        );

        // then
        assertThat(actual).isEqualTo(new ReservationEntity(
                1L,
                "hotteok",
                "2025-01-01",
                TimeEntity.from(timeFixture))
        );
    }

    @DisplayName("데이터베이스에서 모든 방탈출 예약을 조회한다.")
    @Test
    void findAll() {
        // given
        jdbcTemplate.update("INSERT INTO RESERVATION (name, date, time_id) values ('hotteok', '2025-01-01', 1)");
        jdbcTemplate.update("INSERT INTO RESERVATION (name, date, time_id) values ('hotteok', '2025-01-02', 1)");

        // when
        final List<Reservation> actual = reservationDao.findAll();

        // then
        assertThat(actual).containsExactly(
                new Reservation(1L, "hotteok", LocalDate.of(2025, 1, 1), timeFixture),
                new Reservation(2L, "hotteok", LocalDate.of(2025, 1, 2), timeFixture)
        );
    }
}
