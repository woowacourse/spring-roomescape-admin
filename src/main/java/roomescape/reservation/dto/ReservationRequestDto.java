package roomescape.reservation.dto;

public class ReservationRequestDto {
    private final String name;
    private final String date;
    private final long timeId;

    public ReservationRequestDto(final String name, final String date, final long timeId) {
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

    public long getTimeId() {
        return timeId;
    }
}
