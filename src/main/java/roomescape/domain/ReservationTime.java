package roomescape.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ReservationTime {
    private final Long id;
    private final String startAt;

    @JsonCreator
    public ReservationTime(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public ReservationTime(String startAt) {
        this(null, startAt);
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
