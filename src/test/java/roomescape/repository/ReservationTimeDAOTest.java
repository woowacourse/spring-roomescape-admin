package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.model.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeDAOTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ReservationTimeDAO reservationTimeDAO;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("drop table reservation if exists");
        jdbcTemplate.execute("drop table reservation_time if exists");
        jdbcTemplate.execute("""
                create table reservation_time
                (
                    id   bigint       not null AUTO_INCREMENT,
                    start_at varchar(255) not null,
                    primary key (id)
                );
                """
        );
        jdbcTemplate.execute("""
                create table reservation
                (
                    id   bigint       not null AUTO_INCREMENT,
                    name varchar(255) not null ,
                    date varchar(255) not null ,
                    time_id bigint,
                    primary key (id),
                    foreign key (time_id) references reservation_time (id)
                );
                """
        );
        jdbcTemplate.update("insert into reservation_time (start_at) values (?)", "10:00");
        jdbcTemplate.update("insert into reservation_time (start_at) values (?)", "11:00");
    }

    @DisplayName("모든 예약 시간을 조회한다")
    @Test
    void should_get_reservation_times() {
        List<ReservationTime> reservationTimes = reservationTimeDAO.findAllReservations();
        assertThat(reservationTimes).hasSize(2);
    }

    @DisplayName("아이디에 해당하는 예약 시간을 조회한다.")
    @Test
    void should_get_reservation_time() {
        ReservationTime reservationTime = reservationTimeDAO.findReservationTime(1);
        assertThat(reservationTime.getStartAt()).isEqualTo(LocalTime.of(10, 0));
    }

    @DisplayName("예약 시간을 추가한다")
    @Test
    void should_add_reservation_time() {
        reservationTimeDAO.addReservationTime(new ReservationTime(LocalTime.of(12, 0)));
        Integer count = jdbcTemplate.queryForObject("select count(1) from reservation_time", Integer.class);
        assertThat(count).isEqualTo(3);
    }

    @DisplayName("예약 시간을 삭제한다")
    @Test
    void should_delete_reservation_time() {
        reservationTimeDAO.deleteReservationTime(1);
        Integer count = jdbcTemplate.queryForObject("select count(1) from reservation_time", Integer.class);
        assertThat(count).isEqualTo(1);
    }
}
