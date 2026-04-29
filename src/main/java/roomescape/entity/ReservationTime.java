package roomescape.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;

@RequiredArgsConstructor
@Getter
public class ReservationTime {

    private final long id;
    private final LocalTime startAt;

}
