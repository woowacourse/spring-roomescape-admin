package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateReservationRequestDto(String name, @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                          @JsonFormat(pattern = "HH:mm") LocalTime time) {

    public Reservation toEntity() {
        return new Reservation(null, name, date, time);
    }
}
