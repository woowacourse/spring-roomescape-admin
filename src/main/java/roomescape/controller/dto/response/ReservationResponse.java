package roomescape.controller.dto.response;

import java.time.LocalDate;
import roomescape.domain.Reservation;
import roomescape.util.DataTimeFormatterUtils;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        String time
) {

    public static ReservationResponse from(Reservation reservation) {
        String time = DataTimeFormatterUtils.formatToHourMinute(reservation.getTime());
        return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(), time);
    }
}
