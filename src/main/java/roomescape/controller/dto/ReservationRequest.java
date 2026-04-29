package roomescape.controller.dto;

import java.util.regex.Pattern;

public record ReservationRequest(
        String name,
        String date,
        Long timeId
) {
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");

    public ReservationRequest {
        validateNotBlank(name, "이름");
        validateNotBlank(date, "날짜");

        validateDateFormat(date);
    }

    private static void validateNotBlank(String target, String fieldName) {
        if (target == null || target.isBlank()) {
            throw new IllegalArgumentException(fieldName + "은(는) 필수값이며 공백일 수 없습니다.");
        }
    }

    private static void validateDateFormat(String date) {
        if (!DATE_PATTERN.matcher(date).matches()) {
            throw new IllegalArgumentException("올바르지 않은 날짜 형식입니다: " + date);
        }
    }
}
