package roomescape.reservation.service;

import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.reservation.dao.ReservationDao;
import roomescape.reservation.dao.fake.FakeReservationJdbcDao;
import roomescape.reservation.dao.fake.FakeReservationTimeDao;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.time.dao.ReservationTimeDao;
import roomescape.time.domain.ReservationTime;
import roomescape.time.dto.TimeRequest;

class ReservationServiceTest {

    private final ReservationService reservationService;
    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationServiceTest() {
        this.reservationDao = new FakeReservationJdbcDao();
        this.reservationTimeDao = new FakeReservationTimeDao();
        this.reservationService = new ReservationService(reservationDao, reservationTimeDao);
    }

    @AfterEach
    void setUp() {
        reservationDao.removeReservation(1L);
    }

    @DisplayName("요청받은 Reservation에 대해서 create 테스트")
    @Test
    void test1() {
        //given
        TimeRequest timeRequest = new TimeRequest(LocalTime.of(17,48));
        reservationTimeDao.insertTime(timeRequest);

        ReservationRequest reservationRequest = new ReservationRequest(
                "피케이",
                LocalDate.of(2025,4,25),
                1L
        );

        //when
        ReservationResponse insertedReservation = reservationService.createReservation(reservationRequest);

        //then
        Assertions.assertThat(insertedReservation.id()).isNotNull();
    }

    @DisplayName("요청받은 timeId가 존재하지 않는 경우 예약을 생성할 수 없다")
    @Test
    void test2() {
        //given
        TimeRequest timeRequest = new TimeRequest(LocalTime.of(17, 48));
        reservationTimeDao.insertTime(timeRequest);

        ReservationRequest reservationRequest = new ReservationRequest(
                "피케이",
                LocalDate.of(2025,4,25),
                2L
        );

        //when & then
        Assertions.assertThatThrownBy(
                () -> reservationService.createReservation(reservationRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 요청받은 timeId가 존재하지 않습니다.");
    }

    @DisplayName("id를 통한 Reservation 삭제 테스트")
    @Test
    void test3() {
        //given
        Reservation reservation = reservationDao.insertReservation(
                new Reservation(
                        1L,
                        "피케이",
                        LocalDate.of(2025,4,25),
                        new ReservationTime(1L, LocalTime.of(19,35)))
        );
        //when
        reservationService.removeReservation(reservation.getId());

        //then
        Assertions.assertThat(reservationDao.findAllReservations()).isEmpty();
    }

    @DisplayName("모든 예약 정보 조회")
    @Test
    void test4() {
        //given
        reservationDao.insertReservation(
                new Reservation(
                        1L,
                        "피케이",
                        LocalDate.of(2025,4,25),
                        new ReservationTime(1L, LocalTime.of(19,35)))
        );

        //when & then
        Assertions.assertThat(reservationService.findAllReservations().size()).isOne();
    }

}