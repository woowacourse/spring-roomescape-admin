package roomescape.time.controller.dto;

import roomescape.time.domain.ReservationTime;

public record ReservationTimeResponseDto(Long id, String startAt) {

    public static ReservationTimeResponseDto from(ReservationTime time) {
        return new ReservationTimeResponseDto(time.getId(), time.getStartAt().toString());
    }
}
