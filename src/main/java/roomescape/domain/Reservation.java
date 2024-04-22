package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private final Long id;
    private final ClientName clientName;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(final ClientName clientName, final LocalDate date, final LocalTime time) {
        this(0L, clientName, date, time);
    }

    public Reservation(final Long id, final ClientName clientName, final LocalDate date, final LocalTime time) {
        this.id = id;
        this.clientName = clientName;
        this.date = date;
        this.time = time;
    }

    public Reservation initializeIndex(final Long reservationId) {
        return new Reservation(reservationId, clientName, date, time);
    }

    public Long getId() {
        return id;
    }

    public ClientName getClientName() {
        return clientName;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
