package roomescape.model;

import static roomescape.util.FormatValidator.validateTimeFormat;

public class ReservationTime {
    private final Long id;
    private final String startAt;

    public ReservationTime(Long id, String startAt) {
        validateTimeFormat(startAt);
        this.id = id;
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }

}
