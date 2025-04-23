package roomescape.dto;

import roomescape.entity.ReservationTimeEntity;

import java.time.LocalTime;

public record ReservationTimeRequestDto(LocalTime startAt) {
    public ReservationTimeEntity toEntity() {
        return new ReservationTimeEntity(null, startAt);
    }
}
