package roomescape.model;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.dto.ReservationRequestDto;

public class Reservation {
    private Id id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    private Reservation() {
    }

    private Reservation(Id id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation toEntity(ReservationRequestDto reservationDto) {
        return new Reservation(
                new Id(), reservationDto.name(), reservationDto.date(), reservationDto.time()
        );
    }

    public long getId() {
        return id.getValue();
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public boolean isSameId(Id id) {
        return this.id.equals(id);
    }
}
