package roomescape.dto;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public class ReservationCreateDto {
    private String name;
    private String date;
    private long timeId;

    public ReservationCreateDto(String name, String date, long timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public Reservation toDomain() {
        return new Reservation(null, name, date, new ReservationTime(timeId, null));
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public long getTimeId() {
        return timeId;
    }
}
