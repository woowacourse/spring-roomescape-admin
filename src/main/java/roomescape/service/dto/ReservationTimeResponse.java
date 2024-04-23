package roomescape.service.dto;

import roomescape.domain.ReservationTime;

import java.util.List;

public record ReservationTimeResponse(Long id, String startAt) {

    public static ReservationTimeResponse of(ReservationTime reservationTime) {
        return new ReservationTimeResponse(reservationTime.getId(), reservationTime.getStartAt());
    }

    public static List<ReservationTimeResponse> listOf(List<ReservationTime> reservationTimes) {
        return reservationTimes.stream()
                .map(ReservationTimeResponse::of)
                .toList();
    }
}
