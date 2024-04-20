package roomescape.presentation.web.request;

import java.time.LocalDate;

public record CreateReservationRequest(LocalDate date, String name, Long timeId) {
}
