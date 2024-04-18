package roomescape.dto;

import roomescape.entity.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequestDto {

    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public ReservationRequestDto(String name, LocalDate date, LocalTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation toEntity() {
        return new Reservation(null, name, date, time);
    }
}
