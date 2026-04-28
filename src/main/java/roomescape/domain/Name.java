package roomescape.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class Name {
    private String name;

    @JsonCreator
    public Name(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }
}
