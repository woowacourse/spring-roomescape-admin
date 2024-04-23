package roomescape.time.dto;

public class TimeSaveRequest {
    private String startAt;

    public TimeSaveRequest() {
    }

    public TimeSaveRequest(final String startAt) {
        this.startAt = startAt;
    }

    public String getStartAt() {
        return startAt;
    }
}
