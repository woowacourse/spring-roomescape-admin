package roomescape.service.time.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public class ReservationTimeRequestDto {

    private final LocalTime startAt;

    @JsonCreator(mode = Mode.PROPERTIES)
    public ReservationTimeRequestDto(LocalTime startAt) {
        this.startAt = startAt;
    }

    public ReservationTime toReservationTime() {
        return new ReservationTime(null, startAt);
    }
}
