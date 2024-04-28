package roomescape.console.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import roomescape.web.dto.request.ReservationRequest;
import roomescape.web.dto.request.ReservationTimeRequest;

public class InputConverter {

    private InputConverter() {
    }

    public static ReservationRequest toReservationRequest(List<String> body) {
        return new ReservationRequest(body.get(0).trim(), toLocalDate(body.get(1).trim()), toId(body.get(2).trim()));
    }

    public static ReservationTimeRequest toReservationTimeRequest(List<String> body) {
        return new ReservationTimeRequest(toTime(body.get(0).trim()));
    }

    public static Long toId(String stringId) {
        return Long.parseLong(stringId);
    }

    public static Long toId(List<String> stringId) {
        return Long.parseLong(stringId.get(0).trim());
    }

    private static LocalDate toLocalDate(String date) {
        return LocalDate.parse(date);
    }

    private static LocalTime toTime(String time) {
        return LocalTime.parse(time);
    }
}
