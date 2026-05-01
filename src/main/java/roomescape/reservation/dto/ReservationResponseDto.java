package roomescape.reservation.dto;

import roomescape.reservation.Reservation;
import roomescape.reservation.time.dto.ReservationTimeResponseDto;

import java.time.LocalDate;
import java.util.List;

public record ReservationResponseDto(
        Long id,
        String name,
        LocalDate date,
        ReservationTimeResponseDto time
) {
    public static ReservationResponseDto from(Reservation reservation) {
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                ReservationTimeResponseDto.from(reservation.getTime())
        );
    }

    public static List<ReservationResponseDto> from(List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationResponseDto::from)
                .toList();
    }
}
