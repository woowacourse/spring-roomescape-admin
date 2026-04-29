package roomescape.reservation.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReservationEntity {

    private final String name;
    private final LocalDateTime reservedAt;

    public ReservationEntity(String name, LocalDateTime reservedAt) {
        this.name = name;
        this.reservedAt = reservedAt;
    }

    public String getName() {
        return name;
    }

    public LocalDate getReservedDate() {
        return reservedAt.toLocalDate();
    }

    public LocalTime getReservedTime() {
        return reservedAt.toLocalTime();
    }
}
