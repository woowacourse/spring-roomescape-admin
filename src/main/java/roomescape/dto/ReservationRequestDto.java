package roomescape.dto;

public class ReservationRequestDto {
    private final String date;
    private final String name;
    private final String time;

    public ReservationRequestDto(final String date, final String name, final String time) {
        this.date = date;
        this.name = name;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }
}
