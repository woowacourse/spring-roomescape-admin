package roomescape.reservation.dto;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public record ReservationTimeRequest(
        LocalTime startAt
) {

    public ReservationTimeRequest {
        validateTimeFormat(startAt.toString());
    }

    private void validateTimeFormat(String startAt) {
        try {
            LocalTime.parse(startAt, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("예약 시간 형식은 HH:mm 입니다.");
        }
    }
}
