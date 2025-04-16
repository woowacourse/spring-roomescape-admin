package roomescape.dao;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.TestBase;
import roomescape.domain.ReservationDateTime;
import roomescape.domain.ReservationInfo;
import roomescape.entity.Reservation;

class MemoryReservationDaoTest extends TestBase {
    private final LocalDate RESERVATION_DATE = LocalDate.of(2025, 1, 2);
    private final LocalTime RESERVATION_TIME = LocalTime.of(9, 0);

    @Autowired
    private Clock clock;

    private ReservationDateTime RESERVATION_DATE_TIME;
    private MemoryReservationDao memoryReservationDao;

    @BeforeEach
    void setUp() {
        memoryReservationDao = new MemoryReservationDao();
        RESERVATION_DATE_TIME = new ReservationDateTime(
                RESERVATION_DATE,
                RESERVATION_TIME,
                clock
        );
    }

    @Test
    void 예약을_조회한다() {
        // given
        ReservationInfo reservationInfo = new ReservationInfo("name1", RESERVATION_DATE_TIME);
        memoryReservationDao.add(reservationInfo);
        // when
        List<Reservation> reservations = memoryReservationDao.getReservations();

        // thenR
        Assertions.assertThat(reservations).contains(new Reservation(
                1L,
                "name1",
                RESERVATION_DATE,
                RESERVATION_TIME
        ));
    }

    @Test
    void 예약이_존재하는지_확인한다() {
        // given
        ReservationInfo reservationInfo = new ReservationInfo("name1", RESERVATION_DATE_TIME);
        memoryReservationDao.add(reservationInfo);
        // when & then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(memoryReservationDao.existReservation(1L)).isTrue();
        softly.assertThat(memoryReservationDao.existReservation(2L)).isFalse();
        softly.assertAll();
    }

    @Test
    void 예약을_한개_추가한다() {
        // given
        ReservationInfo reservationInfo = new ReservationInfo("name1", RESERVATION_DATE_TIME);
        // when
        memoryReservationDao.add(reservationInfo);
        // then
        List<Reservation> reservations = memoryReservationDao.getReservations();
        Assertions.assertThat(reservations).hasSize(1);
    }

    @Test
    void 예약을_여러개_추가한다() {
        // given
        ReservationInfo reservationInfo1 = new ReservationInfo("name1", RESERVATION_DATE_TIME);
        ReservationInfo reservationInfo2 = new ReservationInfo("name2", RESERVATION_DATE_TIME);
        // when
        memoryReservationDao.add(reservationInfo1);
        memoryReservationDao.add(reservationInfo2);
        // then
        List<Reservation> reservations = memoryReservationDao.getReservations();
        Assertions.assertThat(reservations).contains(
                new Reservation(1L, "name1", RESERVATION_DATE, RESERVATION_TIME),
                new Reservation(2L, "name2", RESERVATION_DATE, RESERVATION_TIME)
        );
    }

    @Test
    void 예약을_삭제한다() {
        // given
        ReservationInfo reservationInfo = new ReservationInfo("name1", RESERVATION_DATE_TIME);
        memoryReservationDao.add(reservationInfo);
        // when
        memoryReservationDao.deleteById(1L);

        // then
        Assertions.assertThat(memoryReservationDao.getReservations()).hasSize(0);
    }
}