package roomescape.console.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import roomescape.core.service.dto.ReservationServiceRequest;
import roomescape.core.service.dto.ReservationTimeServiceRequest;

public class ConsoleInputConverter {

    private static final String ERROR_MESSAGE = "잘못된 입력 형식입니다.";

    public ReservationServiceRequest toReservationServiceRequest(List<String> body) {
        validateReservationSize(body);
        return new ReservationServiceRequest(body.get(0), toLocalDate(body.get(1)), toLong(body.get(2)));
    }

    private void validateReservationSize(List<String> body) {
        if (body.size() != 3) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
    }

    public ReservationTimeServiceRequest toReservationTimeServiceRequest(List<String> body) {
        validateReservationTimeSize(body);
        return new ReservationTimeServiceRequest(toLocalTime(body.get(0)));
    }

    private void validateReservationTimeSize(List<String> body) {
        if (body.size() != 1) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
    }

    public Long toId(List<String> body) {
        return toLong(body.get(0));
    }

    private long toLong(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
    }

    private LocalDate toLocalDate(String input) {
        try {
            return LocalDate.parse(input);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
    }

    private LocalTime toLocalTime(String input) {
        try {
            return LocalTime.parse(input);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
    }
}
