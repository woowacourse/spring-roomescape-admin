package roomescape.repository.dto;

public class ReservationTimeSaveDto {
    private final String startAt;

    public ReservationTimeSaveDto(String startAt) {
        this.startAt = startAt;
    }

    public String getStartAt() {
        return startAt;
    }
}
