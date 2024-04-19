package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
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
}
