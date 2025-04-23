package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.entity.ReservationTime;

public record ReservationTimeResponseDto(Long id,
                                         String startAt) {
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public static ReservationTimeResponseDto from(ReservationTime reservationTime) {
        return new ReservationTimeResponseDto(
                reservationTime.getId(),
                timeFormatter.format(reservationTime.getStartAt())
        );
    }
}
