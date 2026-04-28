package roomescape.domain;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReservationTime {
    private final Long id;
    private final LocalTime startAt;
}
