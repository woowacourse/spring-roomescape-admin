package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.springframework.cglib.core.Local;

public class Reservation {
    private final Long id;
    private final String name;
    private final LocalDateTime time;

    public Reservation(Long id, String name, LocalDateTime time) {
        this.id = id;
        this.name = name;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate(){
        return time.toLocalDate();
    }

    public LocalTime getTime() {
        return time.toLocalTime();
    }
}
