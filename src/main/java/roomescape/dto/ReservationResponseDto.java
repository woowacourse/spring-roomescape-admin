package roomescape.dto;

import roomescape.entity.Reservation;
import roomescape.util.DateTimeFormatUtils;

public record ReservationResponseDto(Long id,
                                     String name,
                                     String date,
                                     String startAt) {

    public static ReservationResponseDto from(Reservation reservation) {
        String date = DateTimeFormatUtils.dateFormatter.format(reservation.getDate());
        String time = DateTimeFormatUtils.timeFormatter.format(reservation.getTime().getStartAt());
        return new ReservationResponseDto(reservation.getId(), reservation.getName(), date, time);
    }
}
