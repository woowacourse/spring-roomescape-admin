package roomescape.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDateTime dateTime;

    public Reservation(final Long id, final String name, final LocalDateTime dateTime) {
        this.id = Objects.requireNonNull(id, "Id는 null이 될 수 없습니다.");
        this.name = Objects.requireNonNull(name, "Name은 null이 될 수 없습니다.");
        this.dateTime = Objects.requireNonNull(dateTime, "DateTime은 null이 될 수 없습니다.");
    }

    public boolean isSameId(final Long givenId) {
        return id.equals(givenId);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
