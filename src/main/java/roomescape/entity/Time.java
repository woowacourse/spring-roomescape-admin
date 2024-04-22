package roomescape.entity;

public class Time {
    private final long id;
    private final String startAt;

    public Time(long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
