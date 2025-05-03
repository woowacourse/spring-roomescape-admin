package roomescape.model;

import java.time.LocalTime;

public class ReservationTime {

    private final Long id;
    private final LocalTime time;

    public ReservationTime(Long id, LocalTime time) {
        validateTime(time);
        this.id = id;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }

    private void validateTime(LocalTime time) {
        if (time == null) {
            throw new IllegalArgumentException("[ERROR] 시간을 입력해주세요.");
        }
    }
}
