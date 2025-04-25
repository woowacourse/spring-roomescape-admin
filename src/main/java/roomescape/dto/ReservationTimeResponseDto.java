package roomescape.dto;

import roomescape.entity.ReservationTime;
import roomescape.util.DateTimeFormatUtils;

public record ReservationTimeResponseDto(Long id,
                                         String startAt) {

    public static ReservationTimeResponseDto from(ReservationTime reservationTime) {
        return new ReservationTimeResponseDto(
                reservationTime.getId(),
                DateTimeFormatUtils.timeFormatter.format(reservationTime.getStartAt())
        );
    }
}
