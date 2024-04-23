package roomescape.dto;

import java.time.LocalDate;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public class ReservationCreateDto {
    private String name;
    private String date;
    private Long timeId;

    public ReservationCreateDto(String name, String date, Long timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public Reservation toDomain() {
        return new Reservation(null, new Name(name), LocalDate.parse(date), new ReservationTime(timeId, null));
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
