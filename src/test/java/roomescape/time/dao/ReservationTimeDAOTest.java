package roomescape.time.dao;

import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.time.domain.ReservationTime;
import roomescape.time.dto.TimeRequest;

@JdbcTest
@Import(TimeDAO.class)
public class ReservationTimeDAOTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TimeDAO timeDAO;

    @AfterEach
    void afterEach() {
        String deleteQuery = "DELETE from reservation_time";
        jdbcTemplate.update(deleteQuery);
    }

    @DisplayName("모든 예약 시간 조회 테스트")
    @Test
    void test1() {
        //given
        String insertQuery = "INSERT into reservation_time (start_at) VALUES (?)";
        jdbcTemplate.update(
                insertQuery,
                LocalTime.of(16, 15)
        );

        //when
        List<ReservationTime> reservationReservationTimes = timeDAO.findAllTimes();

        //then
        Assertions.assertThat(reservationReservationTimes.size()).isOne();
    }

    @DisplayName("예약 시간 추가 테스트")
    @Test
    void test2() {
        //given
        TimeRequest timeRequest = new TimeRequest(
                LocalTime.of(17,5)
        );

        //when
        timeDAO.insertTime(timeRequest);

        //then
        Assertions.assertThat(timeDAO.findAllTimes()).hasSize(1);
    }

    @DisplayName("예약 시간 삭제 테스트")
    @Test
    void test3() {
        //given
        TimeRequest timeRequest = new TimeRequest(
                LocalTime.of(17,5)
        );
        ReservationTime reservationTime = timeDAO.insertTime(timeRequest);

        //when
        timeDAO.deleteTime(reservationTime.getId());

        //then
        Assertions.assertThat(timeDAO.findAllTimes()).hasSize(0);
    }
}
