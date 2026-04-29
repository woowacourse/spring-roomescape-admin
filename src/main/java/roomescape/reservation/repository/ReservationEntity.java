package roomescape.reservation.repository;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationEntity(
        Long id,
        String name,
        LocalDate date,
        LocalTime time
) {
    public static ReservationEntity of(
            String name,
            LocalDate date,
            LocalTime time
    ) {
        return new ReservationEntity(
                null,
                name,
                date,
                time
        );
    }

    public ReservationEntity updateId(Long id) {
        return new ReservationEntity(
                id,
                this.name,
                this.date,
                this.time
        );
    }
}
