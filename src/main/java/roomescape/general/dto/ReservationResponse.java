package roomescape.general.dto;

import java.time.LocalDate;
import java.util.Objects;
import roomescape.general.domain.Reservation;
import roomescape.general.domain.ReservationTime;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        ReservationTime time
) {

    public ReservationResponse {
        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
        Objects.requireNonNull(date);
        Objects.requireNonNull(time);
    }

    public static ReservationResponse from(final Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getReservationTime());
    }
}
