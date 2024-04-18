package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class ReserveTime {

    private final LocalDate date;
    private final LocalTime time;

    public ReserveTime(LocalDate date, LocalTime time) {
        this.date = date;
        this.time = time;
    }

    public ReserveTime(String date, String time) {
        this(LocalDate.parse(date), LocalTime.parse(time));
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReserveTime that = (ReserveTime) o;
        return Objects.equals(date, that.date) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time);
    }
}
