package roomescape.dto;

import java.time.LocalDate;

import roomescape.model.Reservation;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        ReservationTimeResponse time
) {

    public ReservationResponse(final Reservation reservation) {
        this(reservation.getId(), reservation.getName(), reservation.getDate(), new ReservationTimeResponse(
                reservation.getTime().getId(),
                reservation.getTime().getStartAt()
        ));
    }
}
