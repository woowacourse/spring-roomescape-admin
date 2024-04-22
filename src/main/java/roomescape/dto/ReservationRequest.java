package roomescape.dto;

import java.time.LocalDate;

public record ReservationRequest(String name, LocalDate date, long timeId) {

//    public Reservation toReservation(final long id) {
//       return new Reservation(id, name, date, time);
//    }
}
