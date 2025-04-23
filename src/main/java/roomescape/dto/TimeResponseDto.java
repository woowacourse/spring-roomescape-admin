package roomescape.dto;

import roomescape.entity.ReservationTimeEntity;

import java.time.LocalTime;

public record TimeResponseDto(Long id, String startAt) {
    public static TimeResponseDto from(ReservationTimeEntity entity) {
        LocalTime time = entity.startAt();
        return new TimeResponseDto(entity.id(), time.toString());
    }
}
