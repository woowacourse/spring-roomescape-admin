package roomescape.domain;

public class Name {
    private final String name;

    public Name(String name) {
        if (isBlank(name)) {
            throw new IllegalArgumentException("이름은 공백일 수 없습니다.");
        }
        this.name = name;
    }

    private boolean isBlank(String name) {
        return name.isBlank();
    }

    public String getName() {
        return name;
    }
}
