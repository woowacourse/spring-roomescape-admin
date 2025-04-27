package roomescape.presentation.dto;

import roomescape.entity.Reservation;
import roomescape.util.DateTimeFormatUtils;

public record ReservationResponseDto(Long id,
                                     String name,
                                     String date,
                                     ReservationTimeResponseDto time) {

    public static ReservationResponseDto from(Reservation reservation) {
        return getReservationResponseDtoWithId(reservation.getId(), reservation);
    }

    public static ReservationResponseDto fromIdAndReservation(Long id, Reservation reservation) {
        return getReservationResponseDtoWithId(id, reservation);
    }

    private static ReservationResponseDto getReservationResponseDtoWithId(Long id, Reservation reservation) {
        return new ReservationResponseDto(id, reservation.getName(),
                DateTimeFormatUtils.DATE_FORMATTER.format(reservation.getDate()),
                ReservationTimeResponseDto.from(reservation.getTime()));
    }
}
