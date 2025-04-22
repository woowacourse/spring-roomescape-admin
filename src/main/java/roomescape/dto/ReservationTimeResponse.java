package roomescape.dto;

import roomescape.entity.ReservationTime;
import roomescape.util.FormatUtils;

public record ReservationTimeResponse(Long id, String time) {

    public static ReservationTimeResponse toDto(final ReservationTime reservationTime) {

         return new ReservationTimeResponse(
                 reservationTime.getId(),
                 reservationTime.getStartAt().format(FormatUtils.TIME_FORMATTER)
         );
    }
}
