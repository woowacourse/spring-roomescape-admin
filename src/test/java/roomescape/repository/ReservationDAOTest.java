package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationDAOTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ReservationDAO reservationDAO;

    @Autowired
    DataSource dataSource;

    SimpleJdbcInsert reservationTimeInsertActor;

    SimpleJdbcInsert reservationInsertActor;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("drop table reservation if exists");
        jdbcTemplate.execute("drop table reservation_time if exists");
        jdbcTemplate.execute("""
                create table reservation_time
                (
                    id   bigint       not null auto_increment,
                    start_at varchar(255) not null ,
                    primary key (id)
                );
                """
        );
        jdbcTemplate.execute("""
                create table reservation
                (
                    id   bigint       not null auto_increment,
                    name varchar(255) not null ,
                    date varchar(255) not null ,
                    time_id bigint,
                    primary key (id),
                    foreign key (time_id) references reservation_time (id)
                );
                """
        );

        reservationTimeInsertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
        reservationInsertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");

        insertReservationTime("10:00");
        insertReservationTime("11:00");
        insertToReservation("브라운", "2023-08-05", "1");
        insertToReservation("리사", "2023-08-01", "2");
    }

    private void insertReservationTime(String startAt) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("start_at", startAt);
        reservationTimeInsertActor.execute(parameters);
    }

    private void insertToReservation(String name, String date, String timeId) {
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("name", name);
        parameters.put("date", date);
        parameters.put("time_id", timeId);
        reservationInsertActor.execute(parameters);
    }

    @DisplayName("모든 예약을 조회한다")
    @Test
    void should_get_reservation() {
        List<Reservation> reservations = reservationDAO.selectAllReservations();
        assertThat(reservations).hasSize(2);
    }

    @DisplayName("조회한 예약에 예약 시간이 존재한다.")
    @Test
    void should_get_reservation_times() {
        List<Reservation> reservations = reservationDAO.selectAllReservations();
        assertThat(reservations.get(0).getTime().getStartAt()).isEqualTo(LocalTime.of(10, 0));
    }

    @DisplayName("예약을 추가한다")
    @Test
    void should_add_reservation() {
        ReservationTime reservationTime = new ReservationTime(1, LocalTime.of(10, 0));
        reservationDAO.insertReservation(
                new Reservation("네오", LocalDate.of(2024, 9, 1), reservationTime));
        Integer count = jdbcTemplate.queryForObject("select count(1) from reservation", Integer.class);
        assertThat(count).isEqualTo(3);
    }

    @DisplayName("예약을 삭제한다")
    @Test
    void should_delete_reservation() {
        reservationDAO.deleteReservation(1);
        Integer count = jdbcTemplate.queryForObject("select count(1) from reservation", Integer.class);
        assertThat(count).isEqualTo(1);
    }
}
