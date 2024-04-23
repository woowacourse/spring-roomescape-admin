package roomescape.dto;

public class ReservationCreateDto {
    private String name;
    private String date;
    private long timeId;

    public ReservationCreateDto(String name, String date, long timeId) {
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
