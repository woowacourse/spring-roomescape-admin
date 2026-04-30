package roomescape.controller.dto;

public class ReservationTimeCreateRequestDto {
    private final String startAt;

    public ReservationTimeCreateRequestDto(String startAt) {
        this.startAt = startAt;
    }

    public String getStartAt() {
        return startAt;
    }
}
