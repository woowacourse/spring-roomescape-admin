package roomescape.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import roomescape.dto.create.ReservationTimeCreate;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

public class ReservationTimeDaoTest {

    private static final String START_AT_NINE = "09:00";
    private static final String START_AT_TEN = "10:00";
    private static ReservationTimeDao reservationTimeDao;

    @BeforeAll
    static void setUp() {
        DataSource dataSource = new DriverManagerDataSource(
                "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
                "sa",
                ""
        );

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation");
        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation_time");

        jdbcTemplate.execute(
                "CREATE TABLE reservation_time (" +
                        "id BIGINT NOT NULL AUTO_INCREMENT, " +
                        "start_at VARCHAR(255) NOT NULL, " +
                        "PRIMARY KEY (id)" +
                        ")"
        );

        reservationTimeDao = new ReservationTimeDao(jdbcTemplate);
    }

    @BeforeEach
    void clear() {
        reservationTimeDao.deleteAll();
    }

    @Test
    void 데이터베이스에_예약_시간을_추가할_수_있다() {
        // when & then
        assertThat(reservationTimeDao.save(new ReservationTimeCreate(START_AT_TEN)))
                .isEqualTo(1);
    }

    @Test
    void 데이터베이스에서_예약_시간_목록을_가져올_수_있다() {
        // given
        reservationTimeDao.save(new ReservationTimeCreate(START_AT_NINE));
        reservationTimeDao.save(new ReservationTimeCreate(START_AT_TEN));

        // when & then
        assertThat(reservationTimeDao.getAll().size())
                .isEqualTo(2);
    }

    @Test
    void 데이터베이스의_예약_시간을_삭제할_수_있다() {
        // given
        reservationTimeDao.save(new ReservationTimeCreate(START_AT_NINE));
        reservationTimeDao.save(new ReservationTimeCreate(START_AT_TEN));

        Long id = reservationTimeDao.getAll().get(0).id();

        // when & then
        assertThat(reservationTimeDao.delete(id))
                .isEqualTo(1);
    }
}
