package roomescape.service.command;

import java.time.LocalTime;

public class ReservationTimeCommand {
    private LocalTime startAt;

    public ReservationTimeCommand(LocalTime startAt) {
        this.startAt = startAt;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
