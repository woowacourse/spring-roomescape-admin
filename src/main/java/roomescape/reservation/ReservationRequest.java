package roomescape.reservation;

public class ReservationRequest {
    private String name;
    private String date;
    private String time;

    public ReservationRequest() {
    }

    public ReservationRequest(final String name, final String date, final String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
