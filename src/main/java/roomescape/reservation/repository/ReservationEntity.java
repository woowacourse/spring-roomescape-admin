package roomescape.reservation.repository;

import java.time.LocalDate;

public record ReservationEntity(
        Long id,
        String name,
        LocalDate date,
        Long timeId
) {
    public static ReservationEntity of(
            String name,
            LocalDate date,
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
