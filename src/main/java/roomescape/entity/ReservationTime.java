package roomescape.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;

@RequiredArgsConstructor
@Getter
@Builder
public class ReservationTime {

    private final long id;
    private final LocalTime startAt;

}
