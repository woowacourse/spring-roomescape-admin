package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.ReservationRequest;
import roomescape.model.Reservation;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationDaoTest {

    @Autowired
    ReservationDao reservationDao;

    @DisplayName("예약 저장")
    @Test
    void saveReservation() {
        final List<Reservation> beforeSaving = reservationDao.findAll();
        final ReservationRequest reservation = new ReservationRequest("레디", "2024-02-03", "15:00");
        reservationDao.save(reservation);
        final List<Reservation> afterSaving = reservationDao.findAll();

        assertAll(
                () -> assertThat(beforeSaving).isEmpty(),
                () -> assertThat(afterSaving).hasSize(1));
    }

    @DisplayName("예약 삭제")
    @Test
    void removeReservation() {
        final List<Reservation> beforeSaving = reservationDao.findAll();
        final ReservationRequest reservation = new ReservationRequest("레디", "2024-02-03", "15:00");
        reservationDao.save(reservation);
        final List<Reservation> afterSaving = reservationDao.findAll();
        reservationDao.remove(1L);
        final List<Reservation> afterRemoving = reservationDao.findAll();

        assertAll(
                () -> assertThat(beforeSaving).isEmpty(),
                () -> assertThat(afterSaving).hasSize(1),
                () -> assertThat(afterRemoving).isEmpty());
    }

    @DisplayName("단건 조회")
    @Test
    void findById() {
        //given
        final ReservationRequest reservation1 = new ReservationRequest("레디", "2024-02-03", "12:00");
        final ReservationRequest reservation2 = new ReservationRequest("감자", "2024-02-03", "13:00");
        final ReservationRequest reservation3 = new ReservationRequest("오리", "2024-02-03", "14:00");

        final Reservation expected1 = reservation1.toReservation(1L);
        final Reservation expected2 = reservation2.toReservation(2L);
        final Reservation expected3 = reservation3.toReservation(3L);

        final List<ReservationRequest> reservations = List.of(reservation1, reservation2, reservation3);

        for (final ReservationRequest reservation : reservations) {
            reservationDao.save(reservation);
        }

        //when
        final Optional<Reservation> findReservation1 = reservationDao.findById(1L);
        final Optional<Reservation> findReservation2 = reservationDao.findById(2L);
        final Optional<Reservation> findReservation3 = reservationDao.findById(3L);

        //then
        assertAll(
                () -> assertThat(findReservation1).contains(expected1),
                () -> assertThat(findReservation2).contains(expected2),
                () -> assertThat(findReservation3).contains(expected3)
        );
    }

    @DisplayName("전체 조회")
    @Test
    void findAll() {
        //given
        final ReservationRequest reservation1 = new ReservationRequest("레디", "2024-02-03", "12:00");
        final ReservationRequest reservation2 = new ReservationRequest("감자", "2024-02-03", "13:00");
        final ReservationRequest reservation3 = new ReservationRequest("오리", "2024-02-03", "14:00");

        final List<ReservationRequest> reservations = List.of(reservation1, reservation2, reservation3);

        for (final ReservationRequest reservation : reservations) {
            reservationDao.save(reservation);
        }

        final List<Reservation> expected = new ArrayList<>();
        for (long i = 1; i <= 3; i++) {
            expected.add(reservations.get((int) (i - 1)).toReservation(i));
        }

        //when
        final List<Reservation> findAll = reservationDao.findAll();

        //then
        assertThat(findAll).isEqualTo(expected);
    }
}
