package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.domain.Reservation;

public record ReservationResponseDto(Long id, String name, String date, ReservationTimeResponseDto time) {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static ReservationResponseDto from(Reservation reservation) {
        ReservationTimeResponseDto timeResponseDto = ReservationTimeResponseDto.from(reservation.getTime());
        String date = reservation.getDate().format(DATE_FORMATTER);
        return new ReservationResponseDto(
                reservation.getId(), reservation.getName().getValue(), date, timeResponseDto
        );
    }
}
