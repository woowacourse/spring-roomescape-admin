package roomescape.service.dto;

import java.time.LocalTime;
import roomescape.domain.ReservationTime;
import roomescape.utils.TimeFormatter;

public class ReservationTimeDto {

    private final Long id;
    private final String time;

    public ReservationTimeDto(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    public ReservationTimeDto(Long id, LocalTime time) {
        this(id, TimeFormatter.format(time));
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

    public ReservationTime toEntity() {
        if (id == null) {
            throw new IllegalStateException("ID가 존재하지 않습니다.");
        }
        return toEntity(id);
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }
}
