package roomescape.controller.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateReservationRequest(
        String name,
        LocalDate date,
        LocalTime time
) {

    private static final String ERROR_MESSAGE_FORMAT = "[ERROR]  예약 필수 정보가 누락되었습니다. %s: %s";

    public CreateReservationRequest {
        validateName(name);
        validateDate(date);
        validateTime(time);
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(String.format(ERROR_MESSAGE_FORMAT, "name", name));
        }
    }

    private void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException(String.format(ERROR_MESSAGE_FORMAT, "date", date));
        }
    }

    private void validateTime(LocalTime time) {
        if (time == null) {
            throw new IllegalArgumentException(String.format(ERROR_MESSAGE_FORMAT, "time", time));
        }
    }
}
