package roomescape.dto;

public class ReservationTimeRequest {

    private String startAt;

    private ReservationTimeRequest() {
    }

    public ReservationTimeRequest(String startAt) {
        this.startAt = startAt;
    }

    public String getStartAt() {
        return startAt;
    }
}
