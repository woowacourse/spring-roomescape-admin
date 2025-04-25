package roomescape.time.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import roomescape.time.domain.ReservationTime;
import roomescape.time.dto.TimeRequest;

@JdbcTest
@Import(ReservationTimeJdbcDao.class)
public class ReservationTimeJdbcDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationTimeJdbcDao reservationTimeJdbcDao;

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
        List<ReservationTime> reservationReservationTimes = reservationTimeJdbcDao.findAllTimes();

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
        ReservationTime reservationTime = reservationTimeJdbcDao.insertTime(timeRequest);

        //then
        Assertions.assertThat(reservationTime.getId()).isNotNull();
    }

    @DisplayName("예약 시간 삭제 테스트")
    @Test
    void test3() {
        //given
        LocalTime localTime = LocalTime.of(16,20);
        String insertQuery = "INSERT into reservation_time (start_at) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setTime(1, Time.valueOf(localTime));
            return ps;
        }, keyHolder);

        //when
        reservationTimeJdbcDao.deleteTime(keyHolder.getKey().longValue());

        //then
        Assertions.assertThat(reservationTimeJdbcDao.findAllTimes()).hasSize(0);
    }
}
