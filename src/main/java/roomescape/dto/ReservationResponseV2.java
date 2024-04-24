package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.model.Reservation2;
import roomescape.model.ReservationTime;

public record ReservationResponseV2(Long id, String name, String date, TimeResponse time) {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static ReservationResponseV2 from(final Reservation2 reservation2) {
        //TODO: 이름 변경
        final ReservationTime time1 = reservation2.getTime();
        final TimeResponse timeResponse = new TimeResponse(time1.getId(), time1.getStartAt().format(TIME_FORMATTER));
        return new ReservationResponseV2(reservation2.getId(), reservation2.getName(),
                reservation2.getDate().format(DateTimeFormatter.ISO_DATE), timeResponse);
    }
}
