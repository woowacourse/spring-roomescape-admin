package roomescape.domain;

public record Person(String name) {

    private static final int MAX_NAME_LENGTH = 7;

    public Person {
        validateEmptyName(name);
        validateNameLength(name);
    }

    private void validateEmptyName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("비어 있는 이름은 입력 불가합니다.");
        }
    }

    private void validateNameLength(String name) {
        if (overMaxLength(name)) {
            throw new IllegalArgumentException(String.format("이름은 "
                    + "%d자 이하여야 합니다.", MAX_NAME_LENGTH));
        }
    }

    private boolean overMaxLength(String name) {
        return name.length() > MAX_NAME_LENGTH;
    }
}
