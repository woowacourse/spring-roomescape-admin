package roomescape.reservation;

import java.time.LocalDate;
import roomescape.reservationtime.ReservationTime;

public record Reservation(Long id, String name, LocalDate date, ReservationTime time) {

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reservation other)) {
            return false;
        }
        if (this.id == null || other.id == null) {
            return false;
        }
        return this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return (id == null) ? 0 : id.hashCode();
    }
}
