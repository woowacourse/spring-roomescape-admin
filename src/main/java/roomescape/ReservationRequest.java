package roomescape;

public class ReservationRequest {
    private final String name;
    private final String date;
    private final long timeId;
    public ReservationRequest(String name, String date, long timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public long getTimeId() {
        return timeId;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}
