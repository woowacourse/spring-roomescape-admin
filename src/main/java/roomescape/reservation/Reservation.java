package roomescape.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public record Reservation(
        Long id, String name, LocalDate date, LocalTime time
) {

    public Reservation {
        Objects.requireNonNull(name);
        Objects.requireNonNull(date);
        Objects.requireNonNull(time);
    }

    public Reservation writeId(final long writeId) {
        validateCurrentIdEmpty();

        return new Reservation(writeId, name, date, time);
    }

    private void validateCurrentIdEmpty() {
        if (this.id != null) {
            throw new IllegalStateException("[ERROR]");
        }
    }

    public Long id(){
        Objects.requireNonNull(this.id);
        return this.id;
    }
}
