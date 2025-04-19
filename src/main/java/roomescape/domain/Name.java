package roomescape.domain;

public class Name {
    private String value;

    public Name() {
    }

    public Name(String value) {
        validateNull(value);
        this.value = value;
    }

    private void validateNull(final String value) {
        if (value.isBlank()) {
            throw new IllegalArgumentException("이름이 비어있습니다.");
        }
    }

    public static Name from(String value) {
        return new Name(value);
    }

    public String getValue() {
        return value;
    }
}
