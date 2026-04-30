package roomescape.dto;

import roomescape.model.ReservationTime;

import java.time.LocalTime;

public class ReservationTimeResponse {

    private final long id;
    private final String time;

    public ReservationTimeResponse(long id, String time) {
        this.id = id;
        this.time = time;
    }

    public static ReservationTimeResponse from(ReservationTime reservationTime) {
        return new ReservationTimeResponse(
                reservationTime.getId(),
                reservationTime.getStartTime().toString()
        );
    }

    public static ReservationTimeResponse of(ReservationTime reservationTime) {
        return new ReservationTimeResponse(reservationTime.getId(), reservationTime.getStartTime().toString());
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return time;
    }

}
