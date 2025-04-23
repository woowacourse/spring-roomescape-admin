package roomescape.dto;

import roomescape.entity.ReservationTimeEntity;

import java.time.LocalTime;

public record TimeRequestDto(LocalTime startAt) {
    public ReservationTimeEntity toEntity() {
        return new ReservationTimeEntity(null, startAt);
    }
}
