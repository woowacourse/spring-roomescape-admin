package roomescape.dto;

import java.time.LocalTime;
import roomescape.model.Time;

public record TimeResponseDto(
    Long id,
    LocalTime time
) {
    public static TimeResponseDto from(Time time){
        return new TimeResponseDto(
                time.getId(),
                time.getTime()
        );
    }
}
