package roomescape.controller.dto;

import java.util.List;
import roomescape.domain.ReservationTime;

public record ReservationTimesResponseDto(
        List<ReservationTimeResponseDto> times
) {
    public static ReservationTimesResponseDto from(List<ReservationTime> times) {
        return new ReservationTimesResponseDto(times
                .stream()
                .map(ReservationTimeResponseDto::from)
                .toList());
    }
}
