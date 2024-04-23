package roomescape.time.dto;

public class TimeRequest {
    private String startAt;

    public TimeRequest() {
    }

    public TimeRequest(final String startAt) {
        this.startAt = startAt;
    }

    public String getStartAt() {
        return startAt;
    }
}
