package roomescape.dto;

import roomescape.model.Reservation;

public class ReservationResponse {

    private final long id;
    private final String name;
    private final String date;
    private final String time;

    private ReservationResponse(long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate().toString(),
                reservation.getTime().toString()
        );
    }

    public static ReservationResponse of(Long id, ReservationRequest request) {
        return new ReservationResponse(
                id,
                request.getName(),
                request.getDate().toString(),
                request.getTime().toString());
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
