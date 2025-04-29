package roomescape.presentation.dto;

import roomescape.entity.ReservationTime;
import roomescape.util.DateTimeFormatUtils;

public record ReservationTimeResponseDto(Long id,
                                         String startAt) {

    public static ReservationTimeResponseDto from(ReservationTime reservationTime) {
        return getReservationTimeResponseDtoWithId(reservationTime.getId(), reservationTime);
    }

    public static ReservationTimeResponseDto fromIdAndReservationTime(Long id, ReservationTime reservationTime) {
        return getReservationTimeResponseDtoWithId(id, reservationTime);
    }

    private static ReservationTimeResponseDto getReservationTimeResponseDtoWithId(Long id,
                                                                                  ReservationTime reservationTime) {
        return new ReservationTimeResponseDto(
                id, DateTimeFormatUtils.TIME_FORMATTER.format(reservationTime.getStartAt())
        );
    }
}
