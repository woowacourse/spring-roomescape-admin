package roomescape.reservation.repository;

import java.sql.Date;
import java.sql.Time;

public record ReservationEntity(
        Long id,
        String name,
        Date date,
        Time time
) {
    public static ReservationEntity of(
            String name,
            Date date,
            Time time
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
