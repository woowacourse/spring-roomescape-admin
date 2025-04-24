package roomescape.reservation;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.reservation.dao.CollectionReservationDao;
import roomescape.reservationTime.ReservationTime;

class CollectionReservationDaoTest {
    LocalDate date = LocalDate.of(2025, 4, 22);
    ReservationTime time = new ReservationTime(null, LocalTime.of(10, 0));

    Reservation mimiReservation = new Reservation(1L, "mimi", date, time);
    Reservation norangReservation = new Reservation(2L, "norang", date, time);
    Reservation mintReservation = new Reservation(3L, "mint", date, time);


    @DisplayName("예약 정보를 저장할 수 있다.")
    @Test
    void test1() {
        //given
        CollectionReservationDao collectionReservationDao = new CollectionReservationDao();

        //when
        Reservation savedReservation = collectionReservationDao.add(mimiReservation);

        //then
        assertThat(savedReservation).isEqualTo(mimiReservation);
    }

    @DisplayName("예약 정보를 전부 조회할 수 있다.")
    @Test
    void test3() {
        //given
        CollectionReservationDao collectionReservationDao = new CollectionReservationDao(List.of(
                mimiReservation, norangReservation, mintReservation
        ));

        //when
        List<Reservation> all = collectionReservationDao.findAll();

        //then
        assertThat(all).hasSize(3)
                .contains(mimiReservation, norangReservation, mintReservation);
    }

    @DisplayName("id 해당하는 예약 정보를 삭제할 수 있다.")
    @Test
    void test4() {
        //given
        CollectionReservationDao collectionReservationDao = new CollectionReservationDao(new ArrayList<>(List.of(
                mimiReservation, norangReservation, mintReservation
        )));

        Long mimiId = 1L;
        CollectionReservationDao expectedCollectionReservationdao = new CollectionReservationDao(List.of(
                norangReservation, mintReservation
        ));

        //when
        collectionReservationDao.deleteById(mimiId);

        //then
        assertThat(collectionReservationDao).isEqualTo(expectedCollectionReservationdao);
    }
}
