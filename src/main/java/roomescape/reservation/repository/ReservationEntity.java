package roomescape.reservation.repository;

import java.sql.Date;

public record ReservationEntity(
        Long id,
        String name,
        Date date,
        Long timeId
) {
    public static ReservationEntity of(
            String name,
            Date date,
            Long timeId
    ) {
        return new ReservationEntity(
                null,
                name,
                date,
                timeId
        );
    }

    public ReservationEntity updateId(Long id) {
        return new ReservationEntity(
                id,
                this.name,
                this.date,
                this.timeId
        );
    }
}
