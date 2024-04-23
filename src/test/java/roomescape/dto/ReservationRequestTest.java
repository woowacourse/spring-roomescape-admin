package roomescape.dto;

import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ReservationRequestTest {
    @Test
    void convert_Request_to_Reservation(){
        ReservationRequest reservationRequest=new ReservationRequest("abc", LocalDate.of(2024,4,23)
                , LocalTime.of(13,45));
        Reservation reservation = reservationRequest.toReservation(1L);
        Reservation target = new Reservation(1L,"abc", LocalDate.of(2024,4,23)
                ,LocalTime.of(13,45));

        assertThat(reservation).usingRecursiveComparison().isEqualTo(target);
    }
}
