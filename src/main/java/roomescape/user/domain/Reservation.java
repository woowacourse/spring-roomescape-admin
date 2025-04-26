package roomescape.user.domain;

import java.time.LocalDate;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = {"id"})
public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final Long timeId;

    public Reservation(final Long id, final String name, final LocalDate date, final Long timeId) {
        validateNotNull(name, date, timeId);
        this.id = id;
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    private void validateNotNull(final String name, final LocalDate date, final Long timeId) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (timeId == null) {
            throw new IllegalArgumentException("Time cannot be null");
        }
    }
}
