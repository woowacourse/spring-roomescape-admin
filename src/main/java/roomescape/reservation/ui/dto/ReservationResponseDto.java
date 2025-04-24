package roomescape.reservation.ui.dto;

import roomescape.reservation_time.ui.dto.ReservationTimeResponseDto;

import java.time.LocalDate;

public record ReservationResponseDto(Long id,
                                     String name,
                                     LocalDate date,
                                     ReservationTimeResponseDto time) {
}
