package roomescape.dto;

import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ReservationResponseTest {
    @Test
    void convert_Reservation_to_Response(){
        Reservation reservation = new Reservation(1L,"abc", LocalDate.of(2024,4,23)
                , LocalTime.of(13,45));
        ReservationResponse response = ReservationResponse.toDto(reservation);
        ReservationResponse target = new ReservationResponse(1L,"abc", LocalDate.of(2024,4,23)
                , LocalTime.of(13,45));

        assertThat(response).usingRecursiveComparison().isEqualTo(target);
    }
}
