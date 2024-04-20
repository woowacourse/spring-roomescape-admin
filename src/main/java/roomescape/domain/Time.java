package roomescape.domain;

public class Time {
    private Long id;
    private String start_at;

    public Time(Long id, String start_at) {
        this.id = id;
        this.start_at = start_at;
    }

    public Time() {

    }

    public Long getId() {
        return id;
    }

    public String getStart_at() {
        return start_at;
    }
}
