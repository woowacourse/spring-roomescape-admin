package roomescape.dto;

import roomescape.model.Reservation;

public class ReservationResponse {

    private final long id;
    private final String name;
    private final String date;
    private final ReservationTimeResponse time;

    private ReservationResponse(long id, String name, String date, ReservationTimeResponse time) {
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
                ReservationTimeResponse.from(reservation.getTime())
        );
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
        return time.getStartAt();
    }
}
