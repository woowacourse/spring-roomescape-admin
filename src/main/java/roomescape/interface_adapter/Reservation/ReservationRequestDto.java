package roomescape.interface_adapter.Reservation;

import java.time.LocalDate;

public record ReservationRequestDto(LocalDate date, String name, Long timeId) {

}
