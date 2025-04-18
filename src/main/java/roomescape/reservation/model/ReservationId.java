package roomescape.reservation.model;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class ReservationId {

    private static final AtomicLong atomicLong = new AtomicLong(1L);
    private final long id;

    private ReservationId(long id) {
        this.id = id;
    }

    public static ReservationId create() {
        return new ReservationId(atomicLong.getAndIncrement());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReservationId that = (ReservationId) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public long getId() {
        return id;
    }
}
