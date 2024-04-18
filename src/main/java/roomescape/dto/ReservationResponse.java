package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.ReservationInfo;

public class ReservationResponse {
    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public ReservationResponse(ReservationInfo reservationInfo) {
        this.id = reservationInfo.getId().getId();
        this.name = reservationInfo.getName().getName();
        this.date = reservationInfo.getDate();
        this.time = reservationInfo.getTime();
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

    public LocalTime getTime() {
        return time;
    }
}
