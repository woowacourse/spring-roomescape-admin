package roomescape.dto;

import roomescape.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateReservationDto(String name, LocalDate date, LocalTime time) {

    public Reservation convertToEntity(Long id) {
        return new Reservation(id, name, date, time);
    }
}
