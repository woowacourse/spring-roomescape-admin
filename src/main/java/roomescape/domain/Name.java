package roomescape.domain;

import java.util.Objects;

public class Name {

    private final String name;

    public Name(String name) {
        validateBlank(name);
        this.name = name;
    }

    private void validateBlank(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("이름은 빈 칸이거나 공백일 수 없습니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Name name = (Name) o;

        return Objects.equals(this.name, name.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Name{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }
}
