package roomescape.dto;

public class ReservationRequest {

    private String name;
    private String date;
    private Long timeId;

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public Long getTimeId() {
        return timeId;
    }
}
