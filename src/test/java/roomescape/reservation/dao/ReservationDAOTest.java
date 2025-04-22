package roomescape.reservation.dao;

import java.time.LocalDate;
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
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.request.ReservationRequest;

@JdbcTest
@Import(ReservationDAO.class)
public class ReservationDAOTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationDAO reservationDAO;

    @AfterEach
    void afterEach() {
        String deleteQuery = "DELETE from reservation";
        jdbcTemplate.update(deleteQuery);
    }

    @DisplayName("모든 예약자 조회 테스트")
    @Test
    void test1() {
        //given
        String insertQuery = "INSERT into reservation (name, date, time) VALUES (?, ?, ?)";
        jdbcTemplate.update(
                insertQuery,
                "피케이",
                LocalDate.of(2025,4,22), LocalTime.of(17,22)
        );

        //when
        List<Reservation> reservations = reservationDAO.findAllReservations();

        //then
        Assertions.assertThat(reservations.size()).isOne();
    }

    @DisplayName("예약 추가 테스트")
    @Test
    void test2() {
        //given
        ReservationRequest reservationRequest = new ReservationRequest(
                "피케이",
                LocalDate.of(2025,4,22),
                LocalTime.of(17,22)
        );

        //when
        reservationDAO.insertReservation(reservationRequest);

        //then
        Assertions.assertThat(reservationDAO.findAllReservations()).hasSize(1);
    }

    @DisplayName("특정 ID 예약 삭제 테스트")
    @Test
    void test3() {
        //given
        ReservationRequest reservationRequest = new ReservationRequest(
                "피케이",
                LocalDate.of(2025,4,22),
                LocalTime.of(17,22)
        );
        long insertedId = reservationDAO.insertReservation(reservationRequest);

        //when
        reservationDAO.removeReservation(insertedId);

        //then
        Assertions.assertThat(reservationDAO.findAllReservations()).hasSize(0);
    }

}
