package roomescape.controller.dto;

import java.time.LocalDate;

public record CreateReservationRequest(String name, LocalDate date, int timeId) {
}
