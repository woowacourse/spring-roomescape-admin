package roomescape.repository.dto;

public class ReservationSaveDto {
    private final String name;
    private final String date;
    private final Long timeId;

    public ReservationSaveDto(String name, String date, Long timeId) {
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
