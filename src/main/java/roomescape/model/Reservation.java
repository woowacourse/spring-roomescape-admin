package roomescape.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private final String name;
    private final ReservationDateTime reservationDateTime;

    @JsonCreator
    public Reservation(final String name, final ReservationDateTime reservationDateTime) {
        this.name = validateNonBlank(name);
        this.reservationDateTime = reservationDateTime;
    }

    private String validateNonBlank(final String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 이름은 빈 값일 수 없습니다.");
        }

        return name;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return reservationDateTime.getDate();
    }

    public LocalTime getTime() {
        return reservationDateTime.getTime();
    }
}
