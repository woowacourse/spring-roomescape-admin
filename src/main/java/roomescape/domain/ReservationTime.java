package roomescape.domain;

import java.time.LocalTime;

public class ReservationTime {
    private final Long id;
    //TODO StartedAt VS startedTime
    /*
    startedAt : 타입 자체가 Time 을 가지고 있어 의미가 중복된다.
    startedTime : DTO 와 파라미터 이름을 통일 시킬 시 의미가 모호해 진다. (String startedAt)
    */
    private final LocalTime startAt;

    public ReservationTime(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
