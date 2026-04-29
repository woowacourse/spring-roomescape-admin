package roomescape.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Objects;

public class Name {
    private String name;

    @JsonCreator
    public Name(String name) {
        this.name = name;
    }

    public static Name parse(String name) {
        Objects.requireNonNull(name, "name");
        return new Name(name);
    }


    @JsonValue
    public String getName() {
        return name;
    }
}
