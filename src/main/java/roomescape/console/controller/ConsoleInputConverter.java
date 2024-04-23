package roomescape.console.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.springframework.stereotype.Component;
import roomescape.service.dto.ReservationServiceRequest;
import roomescape.service.dto.ReservationTimeServiceRequest;

@Component
public class ConsoleInputConverter {

    public ReservationServiceRequest toReservationServiceRequest(List<String> body) {
        return new ReservationServiceRequest(body.get(0), toLocalDate(body.get(1)), toLong(body.get(2)));
    }

    public ReservationTimeServiceRequest toReservationTimeServiceRequest(List<String> body) {
        return new ReservationTimeServiceRequest(toLocalTime(body.get(0)));
    }

    public Long toId(List<String> body) {
        return toLong(body.get(0));
    }

    private long toLong(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("잘못된 입력 형식입니다.");
        }
    }

    private LocalDate toLocalDate(String input) {
        try {
            return LocalDate.parse(input);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("잘못된 입력 형식입니다.");
        }
    }

    private LocalTime toLocalTime(String input) {
        try {
            return LocalTime.parse(input);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("잘못된 입력 형식입니다.");
        }
    }
}
