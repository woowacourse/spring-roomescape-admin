package roomescape.service.dto;

public class ReservationTimeDto {
    private final long id;
    private final String startAt;

    public ReservationTimeDto(long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
