package roomescape.domain;

public record Person(String name) {
    public Person {
        isInvalidName(name);
    }

    private void isInvalidName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("비어 있는 이름은 입력 불가합니다.");
        }
    }
}
