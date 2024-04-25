package roomescape.console.util;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.reservation.dto.request.ReservationRequest;
import roomescape.reservation.dto.request.ReservationTimeRequest;

public class InputConverter {

    private InputConverter() {
    }

    public static ReservationRequest toReservationRequest(String body) {
        // 검증 추가
        String[] arguments = body.split(",");

        return new ReservationRequest(arguments[0].trim(), toLocalDate(arguments[1].trim()), toId(arguments[2].trim()));
    }

    public static ReservationTimeRequest toReservationTimeRequest(String body) {
        // 검증 추가
        return new ReservationTimeRequest(toTime(body));
    }

    public static Long toId(String body) {
        return Long.parseLong(body);
    }

    private static LocalDate toLocalDate(String date) {
        String[] split = date.split("-");
        return LocalDate.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
    }

    private static LocalTime toTime(String time) {
        String[] split = time.split(":");
        return LocalTime.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }
}
