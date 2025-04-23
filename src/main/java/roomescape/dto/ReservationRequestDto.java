package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import roomescape.model.Reservation;

public record ReservationRequestDto(@JsonProperty(value = "name", defaultValue = "name") String name,
                                    @JsonProperty("date") String date,
                                    @JsonProperty("time") String time) {
    public static Reservation toEntity(Long id, ReservationRequestDto reservationRequestDto) {
        return new Reservation(id, reservationRequestDto.name(), reservationRequestDto.date(), reservationRequestDto.time());
    }
}
