package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Reservation {

    private final Long id;
    private final String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    private final LocalTime time;

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation toEntity(Reservation reservation, Long id) {
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
