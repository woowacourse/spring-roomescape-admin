package roomescape.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {

    private final Long id;
    private final String name;
    private final ReservationDateTime reservationDateTime;

    @JsonCreator
    public Reservation(Long id, String name, ReservationDateTime reservationDateTime) {
        this.id = Objects.requireNonNull(id);
        this.name = validateNonBlank(name);
        this.reservationDateTime = reservationDateTime;
    }

    private String validateNonBlank(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 null이거나 공백일 수 없습니다");
        }

        return name;
    }

    public Long getId() {
        return id;
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
