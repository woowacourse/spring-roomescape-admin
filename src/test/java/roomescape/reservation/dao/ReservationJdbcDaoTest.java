package roomescape.reservation.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import roomescape.reservation.domain.Reservation;
import roomescape.time.domain.ReservationTime;

@JdbcTest
@Import(ReservationJdbcDao.class)
public class ReservationJdbcDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationDao reservationDao;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("DELETE FROM reservation");
        jdbcTemplate.update("DELETE FROM reservation_time");
        jdbcTemplate.update("INSERT INTO reservation_time (id, start_at) VALUES (?, ?)", 1L, LocalTime.of(18, 22));
    }

    @DisplayName("모든 예약자 조회 테스트")
    @Test
    void test1() {
        //given
        String insertQuery = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(
                insertQuery,
                "피케이",
                LocalDate.of(2025,4,22),
                1L
        );

        //when
        List<Reservation> reservations = reservationDao.findAllReservations();

        //then
        Assertions.assertThat(reservations.size()).isOne();
    }

    @DisplayName("예약 추가 테스트")
    @Test
    void test2() {
        //given
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(13,31));

        //when
        Reservation reservation = reservationDao.insertReservation(
                new Reservation(
                        null,
                        "우가",
                        LocalDate.of(2025,4,25),
                        reservationTime));

        //then
        Assertions.assertThat(reservation.getId()).isNotNull();
    }

    @DisplayName("특정 ID 예약 삭제 테스트")
    @Test
    void test3() {
        //given
        String insertQuery = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, "피케이");
            ps.setObject(2, LocalDate.of(2025, 4, 22));
            ps.setLong(3, 1L);
            return ps;
        }, keyHolder);

        Long insertedId = keyHolder.getKey().longValue();

        //when
        reservationDao.removeReservation(insertedId);

        //then
        Assertions.assertThat(reservationDao.findAllReservations()).isEmpty();
    }


}
