package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;

class InMemoryReservationsDaoTest {

    ReservationsDao reservationsDao = new InMemoryReservationsDao();

    @DisplayName("현재 존재하는 모든 예약을 ReservationInfo로 변환하여 반환한다.")
    @Test
    void getReservations_empty() {
        //when
        List<Reservation> reservations = reservationsDao.getReservations();

        //then
        assertThat(reservations).isEmpty();
    }

    @DisplayName("새로운 예약을 추가한다.")
    @Test
    void addReservation() {
        //given
        Reservation reservation1 = new Reservation("name1", LocalDateTime.now());
        Reservation reservation2 = new Reservation("name2", LocalDateTime.now());
        Reservation reservation3 = new Reservation("name3", LocalDateTime.now());

        //when
        Long index1 = reservationsDao.addReservation(reservation1);
        Long index2 = reservationsDao.addReservation(reservation2);
        Long index3 = reservationsDao.addReservation(reservation3);

        //then
        assertThat(reservationsDao.getReservations().size()).isEqualTo(3);
        assertThat(index1).isEqualTo(1);
        assertThat(index2).isEqualTo(2);
        assertThat(index3).isEqualTo(3);
    }

    @DisplayName("id에 해당하는 예약을 삭제한다.")
    @Test
    void deleteReservationById_success() {
        //given
        Reservation reservation = new Reservation("name1", LocalDateTime.now());
        Long id = reservationsDao.addReservation(reservation);

        //when
        reservationsDao.deleteReservationById(id);

        //then
        assertThat(reservationsDao.getReservations()).isEmpty();
    }

    @DisplayName("id에 해당하는 예약이 없으면 예외가 발생한다.")
    @Test
    void deleteReservationById_fail() {
        //when & then
        assertThatThrownBy(() -> reservationsDao.deleteReservationById(1L))
                .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("해당 예약은 존재하지 않습니다.");
    }
}
