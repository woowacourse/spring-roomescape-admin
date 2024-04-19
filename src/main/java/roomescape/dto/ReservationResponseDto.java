package roomescape.dto;

import roomescape.domain.Reservation;

public class ReservationResponseDto {

    private final Long id;
    private final String name;
    private final String date;
    private final String time;

    public ReservationResponseDto(Reservation reservation) {
        this.id = reservation.getId();
        this.name = reservation.getName();
        this.date = reservation.getDate().toString();
        this.time = reservation.getTime().toString();
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

    public String getTime() {
        return time;
    }
}
