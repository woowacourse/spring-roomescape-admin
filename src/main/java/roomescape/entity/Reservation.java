package roomescape.entity;

import java.time.LocalDateTime;

public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDateTime reservedAt;

    public Reservation(Long id, String name, LocalDateTime reservedAt) {
        this.id = id;
        this.name = name;
        this.reservedAt = reservedAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getReservedAt() {
        return reservedAt;
    }
}
