package roomescape.dto;

import roomescape.domain.ReservationTime;

import java.util.List;
import java.util.stream.Collectors;

public class ReservationTimeResponse {

    private final Long id;
    private final String startAt;

    private ReservationTimeResponse(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTimeResponse toResponse(Long id, ReservationTime reservationTime) {
        return new ReservationTimeResponse(id, reservationTime.startAt());
    }

    public static ReservationTimeResponse toResponse(ReservationTime reservationTime) {
        return new ReservationTimeResponse(reservationTime.id(), reservationTime.startAt());
    }

    public static List<ReservationTimeResponse> toReservationTimesResponse(List<ReservationTime> reservationTimes) {
        return reservationTimes.stream()
                .map(ReservationTimeResponse::toResponse)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
