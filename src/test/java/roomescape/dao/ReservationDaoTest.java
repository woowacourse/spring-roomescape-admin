package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static roomescape.TestConstant.TEST_DATE;
import static roomescape.TestConstant.TEST_NAME;
import static roomescape.TestConstant.TEST_START_AT;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationDaoTest {

    @Autowired
    private ReservationDao reservationDao;
    @Autowired
    private ReservationTimeDao reservationTimeDao;

    @DisplayName("예약을 저장한다.")
    @Test
    void save() {
        // given
        ReservationTime reservationTime = new ReservationTime(TEST_START_AT);
        ReservationTime savedReservationTime = reservationTimeDao.save(reservationTime);
        Reservation reservation = new Reservation(TEST_NAME, TEST_DATE, savedReservationTime);

        // when
        Reservation savedReservation = reservationDao.save(reservation);

        // then
        assertAll(
                () -> assertThat(savedReservation.getId()).isEqualTo(1L),
                () -> assertThat(savedReservation.getName()).isEqualTo(reservation.getName()),
                () -> assertThat(savedReservation.getDate()).isEqualTo(reservation.getDate()),
                () -> assertThat(savedReservation.getReservationTime().getId()).isEqualTo(reservation.getReservationTime().getId()),
                () -> assertThat(savedReservation.getReservationTime().getStartAt()).isEqualTo(reservation.getReservationTime().getStartAt())
        );
    }

    @DisplayName("예약을 조회한다.")
    @Test
    void findAll() {
        // given
        ReservationTime reservationTime = new ReservationTime(TEST_START_AT);
        ReservationTime savedReservationTime = reservationTimeDao.save(reservationTime);

        Reservation reservation = new Reservation(TEST_NAME, TEST_DATE, savedReservationTime);
        Reservation savedReservation = reservationDao.save(reservation);

        // when
        List<Reservation> reservations = reservationDao.findAll();

        // then
        Reservation findReservation = reservations.get(0);
        assertAll(
                () -> assertThat(reservations).hasSize(1),
                () -> assertThat(findReservation.getId()).isEqualTo(savedReservation.getId()),
                () -> assertThat(findReservation.getName()).isEqualTo(savedReservation.getName()),
                () -> assertThat(findReservation.getDate()).isEqualTo(savedReservation.getDate()),
                () -> assertThat(findReservation.getReservationTime().getId()).isEqualTo(savedReservation.getReservationTime().getId()),
                () -> assertThat(findReservation.getReservationTime().getStartAt()).isEqualTo(savedReservation.getReservationTime().getStartAt())
        );
    }

    @DisplayName("예약을 삭제한다.")
    @Test
    void delete() {
        // given
        ReservationTime reservationTime = new ReservationTime(TEST_START_AT);
        ReservationTime savedReservationTime = reservationTimeDao.save(reservationTime);

        Reservation reservation = new Reservation(TEST_NAME, TEST_DATE, savedReservationTime);
        Reservation savedReservation = reservationDao.save(reservation);

        // when
        reservationDao.delete(savedReservation.getId());

        // then
        assertThat(reservationDao.findAll()).isEmpty();
    }

    @DisplayName("id값으로 예약을 조회한다.")
    @Test
    void findById() {
        // given
        ReservationTime reservationTime = new ReservationTime(TEST_START_AT);
        ReservationTime savedReservationTime = reservationTimeDao.save(reservationTime);

        Reservation reservation = new Reservation(TEST_NAME, TEST_DATE, savedReservationTime);
        Reservation savedReservation = reservationDao.save(reservation);

        // when
        Reservation findReservation = reservationDao.findById(savedReservation.getId());

        // then
        assertAll(
                () -> assertThat(findReservation.getId()).isEqualTo(savedReservation.getId()),
                () -> assertThat(findReservation.getName()).isEqualTo(savedReservation.getName()),
                () -> assertThat(findReservation.getDate()).isEqualTo(savedReservation.getDate()),
                () -> assertThat(findReservation.getReservationTime().getId()).isEqualTo(savedReservation.getReservationTime().getId()),
                () -> assertThat(findReservation.getReservationTime().getStartAt()).isEqualTo(savedReservation.getReservationTime().getStartAt())
        );
    }
}
