package roomescape.dto;

import roomescape.reservationtime.ReservationTime;

public class ReservationResponseDTO {
    Long id;
    String name;
    String date;
    ReservationTime time;

    public ReservationResponseDTO(Long id, String name, String date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
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

    public ReservationTime getTime() {
        return time;
    }
}
