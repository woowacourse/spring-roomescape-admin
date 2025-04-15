package roomescape.reservation.dto;

import roomescape.reservation.entity.Reservation;

public record CreateReservationResponseDto(Long id) {

    public static CreateReservationResponseDto toDto(Reservation reservation) {
        return new CreateReservationResponseDto(reservation.getId());
    }

}
