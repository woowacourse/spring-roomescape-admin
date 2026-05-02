package roomescape.dto;

public class ReservationTimeRequest {

    private final String startAt;
    public ReservationTimeRequest(String startAt){
        this.startAt = startAt;
    }

    public String getStartAt() {
        return startAt;
    }
}
