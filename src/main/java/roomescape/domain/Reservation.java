package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(
        Long id,
        String name,
        LocalDate date,
        LocalTime time
) {
    public static Reservation withoutId(
            String name,
            LocalDate date,
            LocalTime time
    ) {
        return new Reservation(
                null,
                name,
                date,
                time
        );
    }

    public Reservation identify(long id) {
        return new Reservation(
                id,
                this.name,
                this.date,
                this.time
        );
    }
}
