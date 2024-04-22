package roomescape.service.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.ReservationCreationRequest;

public record ReservationCreationDto(String name, LocalDate date, LocalTime time) {
    public static ReservationCreationDto from(ReservationCreationRequest request) {
        return new ReservationCreationDto(
                request.getName(),request.getDate(), request.getTime()
        );
    }
}
