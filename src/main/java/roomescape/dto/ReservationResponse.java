package roomescape.dto;

import roomescape.domain.Reservation;

public class ReservationResponse {

    private final Long id;
    private final String name;
    private final String date;
    private final ReservationTimeResponse time;

    private ReservationResponse(Long id, String name, String date, ReservationTimeResponse time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                ReservationTimeResponse.from(reservation.getTime()));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public ReservationTimeResponse getTime() {
        return time;
    }
}
