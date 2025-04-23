package roomescape.reservation;

import java.time.LocalDate;
import java.util.Objects;
import roomescape.time.Time;

public record Reservation(
        Long id, String name, LocalDate date, Time time
) {

    public Long id(){
        Objects.requireNonNull(id);
        return id;
    }

    public Time time(){
        Objects.requireNonNull(time);
        return time;
    }
}
