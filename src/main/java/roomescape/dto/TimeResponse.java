package roomescape.dto;

import java.time.LocalTime;

public record TimeResponse(long id, LocalTime startAt) {

    public TimeResponse(long id, TimeRequest timeRequest) {
        this(id, timeRequest.startAt());
    }
}
