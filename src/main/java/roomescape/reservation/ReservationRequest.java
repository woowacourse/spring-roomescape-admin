package roomescape.reservation;

public class ReservationRequest {
    private String name;
    private String date;
    private Long timeId;

    public ReservationRequest() {
    }

    public ReservationRequest(final String name, final String date, final Long timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

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
