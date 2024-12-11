package roomescape.domain;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Time {

    private Long id;
    private LocalTime startAt;

    public Time(LocalTime startAt) {
        this.startAt = startAt;
    }

    public Time(Long id, Time time) {
        this(id, time.getStartAt());
    }
}
