package roomescape;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InMemoryReservationsDaoTest {

    ReservationsDao reservationsDao = new InMemoryReservationsDao();

    @DisplayName("현재 존재하는 모든 예약을 ReservationInfo로 변환하여 반환한다.")
    @Test
    void getReservationsInfo_empty() {
        //when
        List<Reservation> reservations = reservationsDao.getReservationsInfo();

        //then
        assertThat(reservations).isEmpty();
    }
}
