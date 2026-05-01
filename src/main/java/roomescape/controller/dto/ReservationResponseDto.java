package roomescape.controller.dto;

public class ReservationResponseDto {
    private final long id;
    private final String name;
    private final String date;
    private final long timeId;

    public ReservationResponseDto(long id, String name, String date, long timeId) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public long getId() {
        return id;
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
