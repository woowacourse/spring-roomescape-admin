package roomescape.entity;

import java.util.Objects;

public class Name {
    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 5;

    private final String name;

    public Name(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        validateNonNull(name);
        validateLength(name);
    }

    private void validateNonNull(String name) {
        Objects.requireNonNull(name);
    }

    private void validateLength(String name) {
        if (MAX_LENGTH < name.length() || name.length() < MIN_LENGTH) {
            throw new IllegalArgumentException(
                    "예약자 이름은 " + MIN_LENGTH + "자 이상, " + MAX_LENGTH + "자 미만이어야 합니다: " + name);
        }
    }

    public String getName() {
        return name;
    }
}
