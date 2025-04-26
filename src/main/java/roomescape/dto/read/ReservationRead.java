package roomescape.dto.read;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record ReservationRead(
        Long reservationId,
        String name,
        String date,
        Long timeId,
        String timeValue
) {

    public static List<Reservation> toReservations(List<ReservationRead> dtos) {
        return dtos.stream()
                .map(ReservationRead::toReservation)
                .toList();
    }

    private Reservation toReservation() {
        return new Reservation(
                reservationId,
                name,
                LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                new ReservationTime(timeId, LocalTime.parse(timeValue, DateTimeFormatter.ofPattern("HH:mm"))));
    }
}
