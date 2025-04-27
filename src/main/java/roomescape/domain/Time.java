package roomescape.domain;

public class Time {
    private Long id;
    private String startAt;

    public Time(final Long id, final String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Time(final String startAt) {
        this.startAt = startAt;
    }

    public Time() {
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setId(final Long id) {
        this.id = id;
    }
}
