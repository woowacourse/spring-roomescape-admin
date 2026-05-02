package roomescape.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;

public class ReservationTime {
    private Long id;
    
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startAt;

    public ReservationTime() {
    }

    public ReservationTime(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public ReservationTime(LocalTime startAt) {
        this.startAt = startAt;
    }

    public static ReservationTime toEntity(ReservationTime reservationTime, long id) {
        return new ReservationTime(id, reservationTime.startAt);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
