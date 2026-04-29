package roomescape.domain;


import java.util.Objects;

public class Name {
    private static final int MAX_NAME_LENGTH = 7;
    private final String value;

    public Name(String value) {
        validateLength(value);
        this.value = value;
    }

    private void validateLength(String value) {
        if(value == null || value.isEmpty() || value.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("이름은 1~7글자로 이루어져야 합니다.");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Name name)) {
            return false;
        }

        return Objects.equals(value, name.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
