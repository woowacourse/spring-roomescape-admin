package roomescape.reservation.ui.dto;

import roomescape.reservation_time.ui.dto.ReservationTimeResponseDto;

import java.time.LocalDate;

public record ReservationResponseDto(long id,
                                     String name,
                                     LocalDate date,
                                     ReservationTimeResponseDto time) {
}
