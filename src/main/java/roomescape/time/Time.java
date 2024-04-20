package roomescape.time;

public class Time {
    private Long id;
    private String startAt;

    public Time(final Long id, final String startAt) {
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
