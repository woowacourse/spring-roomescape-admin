package roomescape.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.ReservationCreationRequest;

public record ReservationRequest(String name, LocalDate date, LocalTime time) {
    public ReservationCreationRequest toCreationRequest() {
        return new ReservationCreationRequest(name, date, time);
    }
}
