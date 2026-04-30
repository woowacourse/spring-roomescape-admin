package roomescape.time.dto;

public class ReservationTimeRequestDto {

    private final String startAt;

    public ReservationTimeRequestDto(String startAt) {
        this.startAt = startAt;
    }

    public String getStartAt() {
        return startAt;
    }
}
