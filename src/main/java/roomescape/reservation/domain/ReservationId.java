package roomescape.reservation.domain;

public class ReservationId {

    private final Long id;

    private ReservationId(final Long value) {
        this.id = value;
    }

    public static ReservationId from(final Long id) {
        return new ReservationId(id);
    }

    public Long getValue() {
        if (isSaved()) {
            return id;
        }
        throw new IllegalStateException();
    }

    private boolean isSaved() {
        return id != null;
    }
}
