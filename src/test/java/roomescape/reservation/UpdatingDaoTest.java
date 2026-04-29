package roomescape.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(UpdatingDAO.class)
public class UpdatingDaoTest {

    @Autowired
    private UpdatingDAO updatingDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation");
        jdbcTemplate.execute("""
                CREATE TABLE reservation (
                    id   BIGINT       NOT NULL AUTO_INCREMENT,
                    name VARCHAR(255) NOT NULL,
                    date DATE         NOT NULL,
                    time TIME         NOT NULL,
                    PRIMARY KEY (id)
                )""");
    }

    @Test
    @DisplayName("새로운 예약을 추가하면 생성된 ID를 반환한다")
    void insert() {
        Reservation reservation = new Reservation(null, "가현", LocalDate.parse("2026-05-01"), LocalTime.parse("15:30:00"));
        Long generatedId = updatingDAO.insert(reservation);
        assertThat(generatedId).isNotNull();
        Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM reservation WHERE id = ?", Integer.class, generatedId);
        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("예약을 삭제하면 해당 데이터가 DB에서 제거된다")
    void delete() {
        jdbcTemplate.execute("INSERT INTO reservation (name, date, time) VALUES ('가현', '2026-05-01', '13:00:00')");
        Long id = jdbcTemplate.queryForObject("SELECT id FROM reservation LIMIT 1", Long.class);
        int updatedRow = updatingDAO.delete(id);
        assertThat(updatedRow).isEqualTo(1);
        Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM reservation WHERE id = ?", Integer.class, id);
        assertThat(count).isEqualTo(0);
    }
}