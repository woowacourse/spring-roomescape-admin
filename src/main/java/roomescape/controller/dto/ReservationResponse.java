package roomescape.controller.dto;

import roomescape.domain.Reservation;

public record ReservationResponse(
        Long id,
        String name,
        String date,
        ReservationTimeResponse time
) {
    public static ReservationResponse fromDomain(Reservation domain) {
        return new ReservationResponse(
                domain.id(),
                domain.name(),
                domain.date(),
                new ReservationTimeResponse(domain.time().id(), domain.time().startAt())
        );
    }
}
