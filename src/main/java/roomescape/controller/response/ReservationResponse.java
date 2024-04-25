package roomescape.controller.response;

import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservationTime;

import java.time.LocalDate;
import java.util.List;

public record ReservationResponse(Long id, String name, LocalDate date, ReservationTime time) {

    public ReservationResponse(Long id, String name, LocalDate date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(reservation.getId(), reservation.getName().getName(),
                reservation.getDate().getDate(), reservation.getTime());
    }

    public static List<ReservationResponse> listOf(List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }
}
