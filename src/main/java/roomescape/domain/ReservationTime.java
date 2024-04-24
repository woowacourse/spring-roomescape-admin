package roomescape.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.Objects;

public class ReservationTime {
    private final Long id;
    @JsonFormat(pattern = "HH:mm")
    private final LocalTime startAt;

    public ReservationTime(final Long id, final LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTime from(final String startAt) {
        return ReservationTime.from(null, startAt);
    }

    public static ReservationTime from(final Long id, final String startAt) {
        try {
            return new ReservationTime(id, LocalTime.parse(startAt));
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(String.format("%s는 정해진 형식이 아닙니다.", startAt), e);
        }
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
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

    @Override
    public String toString() {
        return "ReservationTime{" +
                "id=" + id +
                ", startAt=" + startAt +
                '}';
    }
}
