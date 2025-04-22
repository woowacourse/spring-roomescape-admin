package roomescape.dto;

import roomescape.entity.TimeEntity;

import java.time.LocalTime;

public record TimeResponseDto(Long id, String startAt) {
    public static TimeResponseDto from(TimeEntity entity) {
        LocalTime time = entity.startAt();
        return new TimeResponseDto(entity.id(), time.toString());
    }
}
