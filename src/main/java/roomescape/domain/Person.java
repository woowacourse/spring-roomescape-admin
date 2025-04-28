package roomescape.domain;

public record Person(String name) {

    private static final int MAX_NAME_LENGTH = 7;

    public Person {
        validateNullName();
        validateEmptyName();
        validateNameLength();
    }

    private void validateNullName() {
        if (name == null) {
            throw new IllegalArgumentException("이름은 없을 수 없습니다.");
        }
    }

    private void validateEmptyName() {
        if (name.isBlank()) {
            throw new IllegalArgumentException("비어 있는 이름은 입력 불가합니다.");
        }
    }

    private void validateNameLength() {
        if (overMaxLength()) {
            throw new IllegalArgumentException(String.format("이름은 %d자 이하여야 합니다.", MAX_NAME_LENGTH));
        }
    }

    private boolean overMaxLength() {
        return name.length() > MAX_NAME_LENGTH;
    }
}
