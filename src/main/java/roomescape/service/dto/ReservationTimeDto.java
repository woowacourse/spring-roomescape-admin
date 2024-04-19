package roomescape.service.dto;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import roomescape.domain.ReservationTime;

public class ReservationTimeDto {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm");

    private final Long id;
    private final String time;

    public ReservationTimeDto(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    public ReservationTimeDto(Long id, LocalTime time) {
        this(id, TIME_FORMATTER.format(time));
    }

    public ReservationTimeDto(LocalTime time) {
        this(null, time);
    }

    public static ReservationTimeDto from(ReservationTime reservationTime) {
        return new ReservationTimeDto(reservationTime.getId(), reservationTime.getTime());
    }

    public ReservationTime toEntity(Long id) {
        return new ReservationTime(id, time);
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }
}
