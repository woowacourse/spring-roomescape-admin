package roomescape.service.dto;

import java.time.LocalTime;
import roomescape.domain.TimeSlot;

public class TimeSlotDto {

    private final Long id;
    private final LocalTime time;

    public TimeSlotDto(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public TimeSlotDto(Long id, String time) {
        this(id, LocalTime.parse(time));
    }

    public TimeSlotDto(LocalTime time) {
        this(null, time);
    }

    public static TimeSlotDto from(TimeSlot timeSlot) {
        return new TimeSlotDto(timeSlot.getId(), timeSlot.getTime());
    }

    public TimeSlot toEntity(Long id) {
        return new TimeSlot(id, time);
    }

    public TimeSlot toEntity() {
        if (id == null) {
            throw new IllegalStateException("ID가 존재하지 않습니다.");
        }
        return toEntity(id);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
