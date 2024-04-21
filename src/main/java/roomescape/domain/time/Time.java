package roomescape.domain.time;

public class Time {

    private final Long id;
    private final StartAt startAt;

    private Time(Long id, StartAt startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static Time of(Long id, String startAt) {
        return new Time(
                id,
                StartAt.from(startAt)
        );
    }

    public Long getId() {
        return id;
    }

    public StartAt getStartAt() {
        return startAt;
    }
}
