package roomescape.reservation.domain.dto;

import roomescape.reservationTime.domain.dto.ReservationTimeResDto;

import java.time.LocalDate;

public record ReservationResDto(Long id, String name, LocalDate date, ReservationTimeResDto reservationTimeResDto) {
}
