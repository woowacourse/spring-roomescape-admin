package roomescape.domain;

public record Name(String value) {
    public Name {
        validateNull(value);
    }

    private void validateNull(final String value) {
        if (value.isBlank()) {
            throw new IllegalArgumentException("이름이 비어있습니다.");
        }
    }

    public static Name from(String value) {
        return new Name(value);
    }
}
