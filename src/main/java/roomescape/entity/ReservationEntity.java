package roomescape.entity;

import roomescape.domain.ClientName;

import java.time.LocalDateTime;

public class ReservationEntity {
    private final Long id;
    private final ClientName clientName;
    private final LocalDateTime time;

    public static ReservationEntity of(final ClientName clientName, final LocalDateTime time) {
        return new ReservationEntity(0L, clientName, time);
    }

    private ReservationEntity(final Long id, final ClientName clientName, final LocalDateTime time) {
        this.id = id;
        this.clientName = clientName;
        this.time = time;
    }

    public ReservationEntity initializeIndex(final Long reservationId) {
        return new ReservationEntity(reservationId, clientName, time);
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
