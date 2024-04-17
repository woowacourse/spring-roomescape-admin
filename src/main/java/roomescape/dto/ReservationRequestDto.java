package roomescape.dto;

public class ReservationRequestDto {
    private String date;
    private String name;
    private String time;

    public ReservationRequestDto() {
    }

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
