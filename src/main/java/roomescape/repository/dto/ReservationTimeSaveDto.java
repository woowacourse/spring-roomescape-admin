package roomescape.repository.dto;

import roomescape.dto.ReservationTimeSaveRequest;

public record ReservationTimeSaveDto(String startAt) {

    public static ReservationTimeSaveDto from(final ReservationTimeSaveRequest reservationTimeSaveRequest) {
        return new ReservationTimeSaveDto(reservationTimeSaveRequest.startAt());
    }
}
