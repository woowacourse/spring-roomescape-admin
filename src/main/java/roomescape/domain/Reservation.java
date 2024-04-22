package roomescape.domain;

import java.time.LocalDate;
import java.util.Objects;

public record Reservation(Long id, String name, LocalDate date, ReservationTime reservationTime) {
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Reservation that = (Reservation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
