package roomescape.reservation.controller.request;

import java.time.LocalDate;

public record ReservationRequest(String name, LocalDate date, Long timeId) {

//    public Reservation toEntity() {
//        return new Reservation(name, date, );
//    }
}
