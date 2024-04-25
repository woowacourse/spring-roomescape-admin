package roomescape.dto;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationResponse {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    private ReservationResponse(Long id, String name, LocalDate date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationResponse toResponse(Reservation reservation) {
        return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public static List<ReservationResponse> toReservationsResponse(List<Reservation> reservations) {
        return reservations.stream().map(ReservationResponse::toResponse).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }
}
