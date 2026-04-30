package roomescape.controller.dto;

import roomescape.domain.ReservationTime;

public record ReservationTimeResponse(
        Long id,
        String startAt
) {
    public static ReservationTimeResponse fromDomain(ReservationTime domain) {
        return new ReservationTimeResponse(
                domain.id(),
                domain.startAt()
        );
    }
}
