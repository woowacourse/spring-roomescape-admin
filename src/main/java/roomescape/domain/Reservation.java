package roomescape.domain;

import java.time.LocalDateTime;

public class Reservation {
    private final Long id;
    private final ClientName clientName;
    private final LocalDateTime time;

    public static Reservation of(final ClientName clientName, final LocalDateTime time) {
        return new Reservation(0L, clientName, time);
    }

    private Reservation(final Long id, final ClientName clientName, final LocalDateTime time) {
        this.id = id;
        this.clientName = clientName;
        this.time = time;
    }

    public Reservation initializeIndex(final Long reservationId) {
        return new Reservation(reservationId, clientName, time);
    }

    public Long getId() {
        return id;
    }

    public ClientName getClientName() {
        return clientName;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
