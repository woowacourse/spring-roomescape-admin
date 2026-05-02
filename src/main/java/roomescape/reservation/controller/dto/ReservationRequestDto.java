package roomescape.reservation.controller.dto;

import java.util.regex.Pattern;

public class ReservationRequestDto {

    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");

    private final String name;
    private final String date;
    private final Long timeId;

    public ReservationRequestDto(String name, String date, Long timeId) {
        validateEmptyName(name);
        validateDateFormat(date);
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    private void validateEmptyName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 비어있을 수 없습니다.");
        }
    }

    private void validateDateFormat(String date) {
        if (date == null || !DATE_PATTERN.matcher(date).matches()) {
            throw new IllegalArgumentException(String.format("날짜 형식이 올바르지 않습니다. (입력값: %s, 기대 형식: yyyy-MM-dd)", date));
        }
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public Long getTimeId() {
        return timeId;
    }
}
