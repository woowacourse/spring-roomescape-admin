package roomescape.dto;

public class ReservationRequestDto {
    private final String name;
    private final String date;
    private final Long timeId;

    public ReservationRequestDto(final String name, final String date, final Long timeId) {
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
