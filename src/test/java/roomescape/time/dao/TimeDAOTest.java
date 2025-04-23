package roomescape.time.dao;

import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.time.domain.Time;

@JdbcTest
@Import(TimeDAO.class)
public class TimeDAOTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TimeDAO timeDAO;


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
        List<Time> reservationTimes = timeDAO.findAllTimes();

        //then
        Assertions.assertThat(reservationTimes.size()).isOne();
    }
}
