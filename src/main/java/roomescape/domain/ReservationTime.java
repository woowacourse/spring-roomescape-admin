package roomescape.domain;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class ReservationTime {
    private final LocalTime startAt;

    public ReservationTime(final LocalTime startAt) {
        this.startAt = startAt;
    }

    public static ReservationTime from(final String startAt) {
        try {
            return new ReservationTime(LocalTime.parse(startAt));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(String.format("%s는 정해진 형식이 아닙니다.", startAt), e);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof final ReservationTime that)) return false;
        return Objects.equals(startAt, that.startAt);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(startAt);
    }
}
