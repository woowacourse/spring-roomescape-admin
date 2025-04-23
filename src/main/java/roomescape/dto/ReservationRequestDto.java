package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

public record ReservationRequestDto(@JsonProperty(value = "name", defaultValue = "name") String name,
                                    @JsonProperty("date") String date,
                                    @JsonProperty("timeId") Long timeId) {
    public static Reservation toEntity(Long id, ReservationRequestDto reservationRequestDto, ReservationTime time) {
        return new Reservation(id, reservationRequestDto.name(), reservationRequestDto.date(), time);
    }
}
