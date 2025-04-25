package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import roomescape.model.Reservation;
import roomescape.model.ReservationDate;
import roomescape.model.ReservationDateTime;
import roomescape.model.ReservationTime;
import roomescape.model.UserName;

public record ReservationRequestDto(@JsonProperty(value = "name", defaultValue = "name") String name,
                                    @JsonProperty("date") String date,
                                    @JsonProperty("timeId") Long timeId) {
    public static Reservation toEntity(Long id, ReservationRequestDto reservationRequestDto, ReservationTime reservationTime) {
        return new Reservation(
                id,
                new UserName(reservationRequestDto.name()),
                new ReservationDateTime(
                        new ReservationDate(reservationRequestDto.date()), reservationTime));
    }
}
