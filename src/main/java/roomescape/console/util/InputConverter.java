package roomescape.console.util;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.web.dto.request.ReservationRequest;
import roomescape.web.dto.request.ReservationTimeRequest;

public class InputConverter {

    public static final String DELIMITER = ",";

    private InputConverter() {
    }

    public static ReservationRequest toReservationRequest(String body) {
        String[] arguments = body.split(DELIMITER);
        return new ReservationRequest(arguments[0].trim(), toLocalDate(arguments[1].trim()), toId(arguments[2].trim()));
    }

    public static ReservationTimeRequest toReservationTimeRequest(String body) {
        return new ReservationTimeRequest(toTime(body));
    }

    public static Long toId(String body) {
        return Long.parseLong(body);
    }

    private static LocalDate toLocalDate(String date) {
        return LocalDate.parse(date);
    }

    private static LocalTime toTime(String time) {
        return LocalTime.parse(time);
    }
}
