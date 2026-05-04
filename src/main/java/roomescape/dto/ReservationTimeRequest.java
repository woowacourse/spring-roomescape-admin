package roomescape.dto;

import java.time.LocalTime;
import roomescape.service.dto.ReservationTimeCreateCommand;

public record ReservationTimeRequest(
        String startAt
) {
    public ReservationTimeCreateCommand toCommand() {
        return new ReservationTimeCreateCommand(LocalTime.parse(this.startAt));
    }
}
