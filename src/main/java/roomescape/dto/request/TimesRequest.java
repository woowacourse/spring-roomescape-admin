package roomescape.dto.request;

import java.util.regex.Pattern;

public record TimesRequest(String startAt) {

    private static final String REGEX = "^(\\d\\d:\\d\\d)$";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    public TimesRequest {
        validate(startAt);
    }

    private void validate(String startAt) {
        if (startAt == null || startAt.isBlank()) {
            throw new IllegalArgumentException("빈 값을 입력했습니다.");
        }

        if (!PATTERN.matcher(startAt).matches()) {
            throw new IllegalArgumentException("잘못된 형식의 시간입니다.");
        }
    }
}
