package roomescape.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public record Reservation(
        long id, String name, LocalDate date, LocalTime time
) {
    private static final long EMPTY_ID = 0;

    public Reservation {
        Objects.requireNonNull(name);
        Objects.requireNonNull(date);
        Objects.requireNonNull(time);
    }

    public Reservation writeId(final long writeId) {
        validateCurrentIdEmpty();
        validateWriteIdEmpty(writeId);

        return new Reservation(writeId, name, date, time);
    }

    private void validateCurrentIdEmpty() {
        if (this.id != EMPTY_ID) {
            throw new IllegalStateException("[ERROR]");
        }
    }

    private static void validateWriteIdEmpty(final long writeId) {
        if (writeId == EMPTY_ID) {
            throw new IllegalStateException("[ERROR]");
        }
    }
}
