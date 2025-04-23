package roomescape.reservation;

import java.time.LocalDate;
import java.util.Objects;

public record Reservation(
        Long id, String name, LocalDate date, Long timeId
) {

    public Reservation {
        Objects.requireNonNull(name);
        Objects.requireNonNull(date);
        Objects.requireNonNull(timeId);
    }

    public Reservation writeId(final long writeId) {
        validateCurrentIdEmpty();

        return new Reservation(writeId, name, date, timeId);
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
