package roomescape.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;

class ReservationAddRequestTest {

    @Test
    @DisplayName("예약 요청시 전달받은 데이터를 바탕으로 Reservation 객체를 생성한다.")
    void createReservationFromReservationAddRequest() {
        String name = "브라운";
        LocalDate date = LocalDate.parse("2024-04-01");
        LocalTime time = LocalTime.parse("07:00");
        ReservationAddRequest reservationAddRequest = new ReservationAddRequest(name, date, time);
        Reservation expected = new Reservation(null, name, date, time);

        Reservation reservation = reservationAddRequest.toReservation();

        assertThat(reservation).isEqualTo(expected);
    }
}
