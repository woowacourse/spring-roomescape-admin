package roomescape.time.controller.dto;

import java.util.regex.Pattern;

public class ReservationTimeRequestDto {

    private static final Pattern TIME_PATTERN = Pattern.compile("^\\d{2}:\\d{2}$");

    private final String startAt;

    public ReservationTimeRequestDto(String startAt) {
        validateTimeFormat(startAt);
        this.startAt = startAt;
    }

    private void validateTimeFormat(String startAt) {
        if (startAt == null || !TIME_PATTERN.matcher(startAt).matches()) {
            throw new IllegalArgumentException(String.format("시간 형식이 올바르지 않습니다. (입력값: %s, 기대 형식: HH:mm)", startAt));
        }
    }

    public String getStartAt() {
        return startAt;
    }
}
