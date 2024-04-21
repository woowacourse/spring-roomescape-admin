package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.entity.ReservationTime;

public class ReservationTimeResponseDto {

    private final Long id;
    private final LocalTime startAt;

    public ReservationTimeResponseDto(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTimeResponseDto from(ReservationTime time) {
        // TODO: entity에서 dto로 변환하는 게 나을까?
        return new ReservationTimeResponseDto(time.getId(), time.getStartAt());
    }

    public Long getId() {
        return id;
    }

    @JsonFormat(pattern = "HH:mm")
    public LocalTime getStartAt() {
        return startAt;
    }
}
