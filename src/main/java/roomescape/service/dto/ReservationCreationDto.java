package roomescape.service.dto;

import java.time.LocalDate;
import roomescape.domain.ReservationCreationRequest;
import roomescape.domain.ReservationTime;

public record ReservationCreationDto(String name, LocalDate date, ReservationTime reservationTime) {
    public static ReservationCreationDto from(ReservationCreationRequest request, ReservationTime reservationTime) {
        return new ReservationCreationDto(
                request.getName(),request.getDate(), reservationTime
        );
    }
}
