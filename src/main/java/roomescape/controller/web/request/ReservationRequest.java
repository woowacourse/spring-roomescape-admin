package roomescape.controller.web.request;

import java.time.LocalDate;

public record ReservationRequest(String name, LocalDate date, Long timeId) {

}
