package roomescape.dto.request;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class ReservationRequest {

    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$");

    private final LocalDate date;
    private final String name;
    private final Long timeId;

    private ReservationRequest(LocalDate date, String name, Long timeId) {
        this.date = date;
        this.timeId = timeId;
        this.name = name;
    }

    public static ReservationRequest of(String name, String date, Long timeId) {
        validateDateAndTime(date);
        return new ReservationRequest(LocalDate.parse(date), name, timeId);
    }

    private static void validateDateAndTime(String date) {
        if (!DATE_PATTERN.matcher(date).matches()) {
            throw new IllegalArgumentException("날짜는 0000-00-00 형식입니다");
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public Long getTimeId() {
        return timeId;
    }
}
