package roomescape.reservation.domain;

import java.time.LocalDateTime;

public class Reservation {

    private final Long id;
    private final ReserverName reserverName;
    private final LocalDateTime dateTime;

    public Reservation(Long id, String reserverName, LocalDateTime dateTime) {
        this.id = id;
        this.reserverName = new ReserverName(reserverName);
        this.dateTime = dateTime;
    }

    public boolean isSameId(final Long id) {
        return this.id.equals(id);
    }

    public Long getId() {
        return id;
    }

    public String getReserverName() {
        return reserverName.getName();
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
