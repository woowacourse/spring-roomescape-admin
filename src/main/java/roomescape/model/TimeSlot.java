package roomescape.model;

import java.time.LocalTime;

public record TimeSlot(
    Long id,
    LocalTime startAt
) {

}
