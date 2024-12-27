package roomescape.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Theme {

    private static final int MIN_NAME_LENGTH = 1;
    private static final int MAX_NAME_LENGTH = 100;

    private Long id;
    private String name;
    private String description;
    private String thumbnail;

    public Theme(String name, String description, String thumbnail) {
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public Theme(Long id, Theme theme) {
        this.id = id;
        this.name = theme.name;
        this.description = theme.description;
        this.thumbnail = theme.thumbnail;
    }
}
