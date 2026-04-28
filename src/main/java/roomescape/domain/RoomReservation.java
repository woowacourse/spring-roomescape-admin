package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class RoomReservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public RoomReservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
