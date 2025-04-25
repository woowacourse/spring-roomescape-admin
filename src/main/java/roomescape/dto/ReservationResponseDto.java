package roomescape.dto;

import roomescape.entity.Reservation;
import roomescape.util.DateTimeFormatUtils;

public record ReservationResponseDto(Long id,
                                     String name,
                                     String date,
                                     ReservationTimeResponseDto time) {

    public static ReservationResponseDto from(Reservation reservation) {
        String date = DateTimeFormatUtils.dateFormatter.format(reservation.getDate());
        return new ReservationResponseDto(reservation.getId(), reservation.getName(), date,
                ReservationTimeResponseDto.from(reservation.getTime()));
    }
}
