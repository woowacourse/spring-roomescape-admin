package roomescape.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import roomescape.model.Reservation;

public record ReservationResponse(long id, String name, LocalDate date, String time) {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private static ReservationResponse of(Reservation reservation) {
        return new ReservationResponse(
                reservation.id(),
                reservation.name(),
                reservation.date(),
                reservation.time().format(FORMATTER)
        );
    }

    public static List<ReservationResponse> toResponses(List<Reservation> reservations) {
        return reservations.stream().map(ReservationResponse::of).toList();
    }
}
