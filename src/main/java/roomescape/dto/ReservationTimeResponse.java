package roomescape.dto;

import roomescape.entity.ReservationTimeEntity;

import java.time.LocalTime;

public record ReservationTimeResponse(Long id, String startAt) {
    public static ReservationTimeResponse from(ReservationTimeEntity entity) {
        LocalTime time = entity.startAt();
        return new ReservationTimeResponse(entity.id(), time.toString());
    }
}
