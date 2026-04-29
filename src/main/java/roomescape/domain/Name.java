package roomescape.domain;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.DELEGATING;

import com.fasterxml.jackson.annotation.JsonCreator;

public record Name(String value) {
    private static final int MAX_LENGTH = 10;

    @JsonCreator(mode = DELEGATING)
    public Name {
        if (value == null) {
            throw new IllegalArgumentException("[ERROR] 이름은 null일 수 없습니다.");
        }

        String trimmed = value.trim();

        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 이름은 비어 있을 수 없습니다.");
        }

        if (trimmed.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("[ERROR] 이름은 " + MAX_LENGTH + "자를 초과할 수 없습니다.");
        }

        value = trimmed;
    }
}
