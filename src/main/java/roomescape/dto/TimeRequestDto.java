package roomescape.dto;

import roomescape.entity.TimeEntity;

import java.time.LocalTime;

public record TimeRequestDto(LocalTime startAt) {
    public TimeEntity toEntity() {
        return new TimeEntity(null, startAt);
    }
}
