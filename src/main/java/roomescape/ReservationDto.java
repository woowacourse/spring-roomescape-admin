package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record ReservationDto(
        Long id,
        String name,
        String date,
        String time
) {
    public static ReservationDto toDto(Reservation reservation) {
        return new ReservationDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate().toString(),
                reservation.getTime().toString()
        );
    }

    public Reservation toEntity() {
        return new Reservation(
                null,
                name,
                LocalDate.parse(date),
                LocalTime.parse(time)
        );
    }
}
