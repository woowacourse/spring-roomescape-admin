package roomescape.presentation.dto.response;

import roomescape.business.domain.Reservation;

import java.time.LocalDate;
import java.util.Objects;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        ReservationTimeResponse time
) {
    public ReservationResponse {
        Objects.requireNonNull(id, "id가 설정되지 않았습니다.");
        Objects.requireNonNull(name, "예약자명이 설정되지 않았습니다.");
        Objects.requireNonNull(date, "날짜가 설정되지 않았습니다.");
        Objects.requireNonNull(time, "시간이 설정되지 않았습니다.");
    }

    public static ReservationResponse from(final Reservation reservation, final Long reservationId, final Long timeId) {
        return new ReservationResponse(
                reservationId,
                reservation.customerName(),
                reservation.date(),
                ReservationTimeResponse.from(reservation.reservationTime(), timeId)
        );
    }
}
