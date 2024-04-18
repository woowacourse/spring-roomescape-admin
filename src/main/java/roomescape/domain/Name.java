package roomescape.domain;

import java.util.Objects;

public class Name {

    private final String name;

    public Name(String name) {
        this.name = name;
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
}
