package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import roomescape.model.Reservation;
import roomescape.model.ReservationTimeSlot;

public record CreateReservationRequest(
    String name,
    LocalDate date,
    @JsonProperty("timeId") Long timeSlotId
) {

    public Reservation toReservation(final long id, final ReservationTimeSlot timeSlot) {
        return new Reservation(id, name, date, timeSlot);
    }
}
