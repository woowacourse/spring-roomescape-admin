package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import roomescape.model.Reservation;
import roomescape.model.ReservationDate;
import roomescape.model.ReservationDateTime;
import roomescape.model.ReservationTime;
import roomescape.model.UserName;

public record ReservationRequestDto(@JsonProperty(value = "name", defaultValue = "name") String name,
                                    @JsonProperty("date") String date,
                                    @JsonProperty("time_id") Long time_id) {

    public Reservation toEntity(Long id, ReservationTime reservationTime) {
        return new Reservation(id,
                new UserName(name()),
                new ReservationDateTime(new ReservationDate(date()), reservationTime));
    }
}
