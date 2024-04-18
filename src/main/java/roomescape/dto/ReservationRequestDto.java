package roomescape.dto;

import roomescape.entity.ReservationEntity;
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

    public ReservationEntity toEntity() {
        return new ReservationEntity(name, date, time);
    }
}
