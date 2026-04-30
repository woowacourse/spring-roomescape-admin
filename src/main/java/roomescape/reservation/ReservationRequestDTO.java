package roomescape.reservation;

public class ReservationRequestDTO {
    String name;
    String date;
    Long timeId;

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public Long getTimeId() {
        return timeId;
    }

    public ReservationRequestDTO(String name, String date, Long timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }
}
