package roomescape;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;

class ReservationDaoTest {

    private ReservationDao reservationDao;

    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        DataSource dataSource = DataSourceBuilder.create().url("jdbc:h2:mem:database-test").username("sa").build();
        jdbcTemplate = new JdbcTemplate(dataSource);
        reservationDao = new ReservationDao(dataSource);
        String createTableSql = """
                DROP TABLE IF EXISTS reservation, reservation_time;
                
                CREATE TABLE reservation_time
                (
                    id       BIGINT       NOT NULL AUTO_INCREMENT,
                    start_at VARCHAR(255) NOT NULL,
                    PRIMARY KEY (id)
                );
                
                CREATE TABLE reservation
                (
                    id      BIGINT       NOT NULL AUTO_INCREMENT,
                    name    VARCHAR(255) NOT NULL,
                    date    VARCHAR(255) NOT NULL,
                    time_id BIGINT,
                    PRIMARY KEY (id),
                    FOREIGN KEY (time_id) REFERENCES reservation_time (id)
                );
                """;
        jdbcTemplate.execute(createTableSql);
        String insertSql = """
                INSERT INTO RESERVATION_TIME(id, start_at) VALUES 
                   ('1', '13:40'), 
                   ('2', '14:40'),
                   ('3', '15:40')
                ;
                
                INSERT INTO RESERVATION(name, date, time_id) VALUES
                    ('브라운', '2023-03-03', '1'),
                    ('솔라', '2023-03-03', '2'),
                    ('네오', '2023-03-05', '3')
                ;
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
                new ReservationTime(
                        1L,
                        LocalTime.of(13, 40)
                )
        );

        Reservation saved = reservationDao.save(reservation);

        Reservation found = jdbcTemplate.queryForObject(
                """
                        SELECT
                            r.id as reservation_id,
                            r.name,
                            r.date,
                            t.id as time_id,
                            t.start_at as time_value
                        FROM reservation as r
                        inner join reservation_time as t
                        on r.time_id = t.id
                        where r.id = ?;
                        """,
                (rs, rowNum) -> new Reservation(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getDate("date").toLocalDate(),
                        new ReservationTime(
                                rs.getLong("time_id"),
                                rs.getTime("time_value").toLocalTime()
                        )
                ), saved.getId());

        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo(saved.getName());
        assertThat(found.getDate()).isEqualTo(saved.getDate());
        assertThat(found.getTime().getStartAt()).isEqualTo(saved.getTime().getStartAt());
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
        List<Reservation> reservations = jdbcTemplate.query("""
                        SELECT
                            r.id as reservation_id,
                            r.name,
                            r.date,
                            t.id as time_id,
                            t.start_at as time_value
                        FROM reservation as r
                        inner join reservation_time as t
                        on r.time_id = t.id
                        """,
                (rs, rowNum) -> new Reservation(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getDate("date").toLocalDate(),
                        new ReservationTime(
                                rs.getLong("time_id"),
                                rs.getTime("time_value").toLocalTime()
                        )
                ));
        assertThat(deleted).isEqualTo(1);
        assertThat(reservations).hasSize(2);
    }
}
