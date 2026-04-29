package roomescape.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(QueryingDAO.class)
public class QueryingDaoTest {

    @Autowired
    private QueryingDAO queryingDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation");
        jdbcTemplate.execute("""
                CREATE TABLE reservation (
                    id   BIGINT       NOT NULL AUTO_INCREMENT,
                    name VARCHAR(255) NOT NULL,
                    date VARCHAR(255) NOT NULL,
                    time VARCHAR(255) NOT NULL,
                    PRIMARY KEY (id)
                )""");
        jdbcTemplate.execute("INSERT INTO reservation (name, date, time) VALUES ('현미밥', '2026-04-29', '10:00:00')");
        jdbcTemplate.execute("INSERT INTO reservation (name, date, time) VALUES ('테리', '2026-04-30', '11:24:00')");
        jdbcTemplate.execute("INSERT INTO reservation (name, date, time) VALUES ('주니', '2026-05-05', '21:38:00')");
    }



    @Test
    @DisplayName("전체 예약 목록을 조회한다")
    void findAll() {
        List<Reservation> reservations = queryingDAO.findAll();

        assertThat(reservations).hasSize(3);
        assertThat(reservations.get(0).getName()).isEqualTo("현미밥");
        assertThat(reservations.get(1).getName()).isEqualTo("테리");
        assertThat(reservations.get(2).getName()).isEqualTo("주니");
    }
}
