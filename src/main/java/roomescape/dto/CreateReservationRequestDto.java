package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateReservationRequestDto(String name, LocalDate date, @JsonFormat(pattern = "HH:mm") LocalTime time) {

    public Reservation toEntity(Long id) {
        return new Reservation(id, name, date, time);
    }
}
