package roomescape.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
public class Theme {

    private static final int MIN_NAME_LENGTH = 1;
    private static final int MAX_NAME_LENGTH = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
