package roomescape;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ReservationDaoTest {

    @Autowired
    private ReservationDao reservationDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        String createTableSql = """
                DROP TABLE IF EXISTS RESERVATION;
                CREATE TABLE reservation
                (
                    id   BIGINT       NOT NULL AUTO_INCREMENT,
                    name VARCHAR(255) NOT NULL,
                    date VARCHAR(255) NOT NULL,
                    time VARCHAR(255) NOT NULL,
                    PRIMARY KEY (id)
                );
                """;
        jdbcTemplate.execute(createTableSql);
        String insertSql = """
                INSERT INTO RESERVATION(name, date, time) VALUES
                    ('브라운', '2023-03-03', '13:40'),
                    ('솔라', '2023-03-03', '14:40'),
                    ('네오', '2023-03-05', '15:40')
                """;
        jdbcTemplate.update(insertSql);
    }

    @DisplayName("예약을 저장한다")
    @Test
    void insertTest() {
        Reservation reservation = new Reservation(
                null,
                "검프",
                LocalDate.of(2023, 3, 4),
                LocalTime.of(14, 20)
        );

        Reservation saved = reservationDao.save(reservation);

        Reservation found = jdbcTemplate.queryForObject("SELECT * FROM RESERVATION WHERE id = ?",
                (rs, rowNum) -> new Reservation(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getDate("date").toLocalDate(),
                        rs.getTime("time").toLocalTime()
                ), saved.getId());
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo(saved.getName());
        assertThat(found.getDate()).isEqualTo(saved.getDate());
        assertThat(found.getTime()).isEqualTo(saved.getTime());
    }

    @DisplayName("Id로 예약을 조회한다")
    @Test
    void selectByIdTest() {
        // given
        Long id = 1L;

        // when
        Optional<Reservation> found = reservationDao.findById(id);

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("브라운");
    }

    @DisplayName("모든 예약을 조회한다")
    @Test
    void selectAllTest() {
        // when
        List<Reservation> reservations = reservationDao.findAll();

        // then
        assertThat(reservations).hasSize(3);
    }

    @DisplayName("Id로 예약을 삭제한다")
    @Test
    void removeByIdTest() {
        // given
        Long id = 1L;

        // when
        int deleted = reservationDao.delete(id);

        // then
        List<Reservation> reservations = jdbcTemplate.query("SELECT * FROM RESERVATION",
                (rs, rowNum) -> new Reservation(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getDate("date").toLocalDate(),
                        rs.getTime("time").toLocalTime()
                ));
        assertThat(deleted).isEqualTo(1);
        assertThat(reservations).hasSize(2);
    }
}
