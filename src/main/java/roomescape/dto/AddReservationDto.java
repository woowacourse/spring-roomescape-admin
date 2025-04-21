package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import roomescape.model.Reservation;

public record AddReservationDto(@JsonProperty(value = "name", defaultValue = "name") String name,
                                @JsonProperty("date") String date,
                                @JsonProperty("time") String time) {
    public static Reservation toEntity(Long id, AddReservationDto addReservationDto) {
        return new Reservation(id, addReservationDto.name(), addReservationDto.date(), addReservationDto.time());
    }
}
