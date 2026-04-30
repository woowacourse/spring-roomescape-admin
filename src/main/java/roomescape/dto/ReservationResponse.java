package roomescape.dto;

import roomescape.domain.Reservation;

public class ReservationResponse {

    private Long id;
    private String name;
    private String date;
    private TimeResponse timeResponse;

    private ReservationResponse(Long id, String name, String date, TimeResponse timeResponse) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.timeResponse = timeResponse;
    }

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                TimeResponse.from(reservation.getTime())
        );
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

    public TimeResponse getTimeResponse() {
        return timeResponse;
    }
}
