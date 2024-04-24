package roomescape.domain;

import java.util.Objects;

public record Name(String value) {
    private static final int MIN_VALUE = 2;
    private static final String MIN_ERROR_MESSAGE = String.format("이름은 %d보다 길어야 합니다.", MIN_VALUE);

    public Name {
        validate(value);
    }

    private static void validate(String value) {
        Objects.requireNonNull(value);
        if (value.length() < MIN_VALUE) {
            throw new IllegalArgumentException(MIN_ERROR_MESSAGE);
        }
    }
}
