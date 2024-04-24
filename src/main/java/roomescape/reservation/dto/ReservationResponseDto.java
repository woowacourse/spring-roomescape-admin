package roomescape.reservation.dto;

import roomescape.reservation.domain.Reservation;
import roomescape.time.dto.ReservationTimeResponseDto;

public record ReservationResponseDto(long id, String name, String date, ReservationTimeResponseDto time) {

    public static ReservationResponseDto from(final Reservation reservation) {
        return new ReservationResponseDto(reservation.id(), reservation.name(), reservation.date().toString(), ReservationTimeResponseDto.from(reservation.time()));
    }
}
