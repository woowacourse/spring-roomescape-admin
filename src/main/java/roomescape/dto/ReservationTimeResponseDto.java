package roomescape.dto;

import roomescape.entity.ReservationTimeEntity;

import java.time.LocalTime;

public record ReservationTimeResponseDto(Long id, String startAt) {
    public static ReservationTimeResponseDto from(ReservationTimeEntity entity) {
        LocalTime time = entity.startAt();
        return new ReservationTimeResponseDto(entity.id(), time.toString());
    }
}
