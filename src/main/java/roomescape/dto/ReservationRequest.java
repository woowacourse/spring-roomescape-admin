package roomescape.dto;

import java.time.LocalDate;
import roomescape.service.dto.ReservationCreateCommand;

public record ReservationRequest(
        String date,
        String name,
        Long timeId
) {
    public ReservationCreateCommand toCommand() {
        return new ReservationCreateCommand(
                this.name,
                LocalDate.parse(this.date),
                this.timeId
        );
    }
}
