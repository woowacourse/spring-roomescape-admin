package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDateTime localDate;

    public Reservation(Long id, String name, LocalDateTime localDate) {
        this.id = id;
        this.name = name;
        this.localDate = localDate;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getLocalDate() {
        return localDate;
    }
}
