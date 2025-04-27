package roomescape.adapter.reservation;

import java.time.LocalDate;
import roomescape.adapter.reservationtime.ReservationTimeResponseDto;
import roomescape.usecase.reservation.ReservationOutput;

public record ReservationResponseDto(Long id, String name, LocalDate date, ReservationTimeResponseDto time) {
    public static ReservationResponseDto from(ReservationOutput output) {
        return new ReservationResponseDto(
                output.id(),
                output.name(),
                output.date(),
                ReservationTimeResponseDto.from(output.reservationTimeOutput())
        );
    }
}

