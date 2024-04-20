package roomescape.repository.dto;

import roomescape.dto.ReservationSaveRequest;
import roomescape.model.ReservationTime;

public record ReservationSaveDto(String name, String date, ReservationTime time) {

    public static ReservationSaveDto of(final ReservationSaveRequest reservationSaveRequest, final ReservationTime time) {
        return new ReservationSaveDto(reservationSaveRequest.name(), reservationSaveRequest.date(), time);
    }
}
