package roomescape.service.command;

import roomescape.dto.TimeRequestDto;

import java.time.LocalTime;

public class CreateTimeCommand {
    private final LocalTime time;

    private CreateTimeCommand(LocalTime time) {
        this.time = time;
    }

    public static CreateTimeCommand from(TimeRequestDto timeRequest) {
        return new CreateTimeCommand(timeRequest.getStartAt());
    }

    public LocalTime getTime() {
        return time;
    }
}
