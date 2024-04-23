package roomescape.dto;

public class ReservationTimeCreateDto {
    private String startAt;

    public ReservationTimeCreateDto() {
    }

    public ReservationTimeCreateDto(String startAt) {
        this.startAt = startAt;
    }

    public String getStartAt() {
        return startAt;
    }
}
