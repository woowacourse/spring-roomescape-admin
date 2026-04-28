package roomescape.time.entity;

public class Time {

    private final Long id;

    private final String startAt;

    public Time(Long id, String startAt) {
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
