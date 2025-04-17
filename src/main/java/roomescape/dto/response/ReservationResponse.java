package roomescape.dto.response;

import java.time.LocalDate;
import roomescape.Reservation;
import roomescape.util.DataTimeFormatterUtils;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        String time
) {

    public static ReservationResponse from(Reservation reservation) {
        String time = DataTimeFormatterUtils.formatToHourMinute(reservation.time());
        return new ReservationResponse(reservation.id(), reservation.name(), reservation.date(), time);
    }
}
