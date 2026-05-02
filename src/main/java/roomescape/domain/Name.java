package roomescape.domain;

import java.util.Objects;

public class Name {
    private String name;

    public Name(String name) {
        this.name = name;
    }

    public static Name parse(String name) {
        Objects.requireNonNull(name, "name");
        return new Name(name);
    }

    public String toString() {
        return name;
    }
}
