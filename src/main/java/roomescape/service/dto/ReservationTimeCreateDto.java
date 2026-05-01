package roomescape.service.dto;

public class ReservationTimeCreateDto {
    private final String startAt;

    public ReservationTimeCreateDto(String startAt) {
        this.startAt = startAt;
    }

    public String getStartAt() {
        return startAt;
    }
}
