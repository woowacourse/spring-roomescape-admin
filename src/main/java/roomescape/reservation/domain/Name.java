package roomescape.reservation.domain;

public class Name {
    private String name;

    public Name(final String name) {
        validateNameIsBlank(name);
        this.name = name;
    }

    private void validateNameIsBlank(final String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("빈 이름을 사용할 수 없습니다.");
        }
    }

    public String getName() {
        return name;
    }
}
