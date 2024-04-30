package roomescape.dto.request;

import java.util.regex.Pattern;

public record ReservationsRequest(String name, String date, Long timeId) {

    private static final String REGEX = "^(\\d\\d\\d\\d-\\d\\d-\\d\\d)$";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    public ReservationsRequest {
        validate(name, date, timeId);
    }

    private void validate(String name, String date, Long timeId) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("빈 값을 입력했습니다.");
        }
        if (date == null || date.isBlank() || !PATTERN.matcher(date).matches()) {
            throw new IllegalArgumentException("잘못된 형식의 날짜입니다>");
        }
        if (timeId < 0) {
            throw new IllegalArgumentException("잘못된 ID 값입니다.");
        }
    }
}
