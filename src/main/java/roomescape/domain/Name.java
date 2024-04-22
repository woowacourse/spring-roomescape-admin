package roomescape.domain;

import java.util.Objects;

public class Name {

    private static final int MAX_LENGTH = 20;

    private final String name;

    public Name(String name) {
        validateNotEmpty(name);
        validateLength(name);
        this.name = name;
    }

    private void validateNotEmpty(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 공백일 수 없습니다.");
        }
    }

    private void validateLength(String name) {
        if (name != null && name.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("이름의 길이는 " + MAX_LENGTH + "자를 넘을 수 없습니다.");
        }
    }

    public String asText() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Name that = (Name) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Name{" +
                "name='" + name + '\'' +
                '}';
    }
}
