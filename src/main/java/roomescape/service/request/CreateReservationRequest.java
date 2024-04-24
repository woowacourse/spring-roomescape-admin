package roomescape.service.request;

import java.time.LocalDate;

public record CreateReservationRequest(LocalDate date, String name, long timeId) {
}
