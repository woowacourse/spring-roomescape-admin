package roomescape.domain;

public class Time {

    private Long id;
    private String startAt;

    private Time() {
    }

    public Time(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Time(String startAt) {
        this(null, startAt);
    }

    public static Time toEntity(Long id, Time time) {
        return new Time(id, time.getStartAt());
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
