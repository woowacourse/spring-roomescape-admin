package roomescape.data.dto.request;

import java.time.LocalDate;
import java.util.Objects;

public class ReservationRequest {
    private final String name;
    private final LocalDate date;
    private final long timeId;

    public ReservationRequest(final String name, final LocalDate date, final long timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public long getTimeId() {
        return timeId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final var that = (ReservationRequest) o;
        return timeId == that.timeId && Objects.equals(name, that.name) && Objects.equals(date,
                that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date, timeId);
    }
}
