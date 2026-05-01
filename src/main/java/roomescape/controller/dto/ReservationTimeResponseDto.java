package roomescape.controller.dto;

public class ReservationTimeResponseDto {
    private final long id;
    private final String startAt;

    public ReservationTimeResponseDto(long id, String startAt) {
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
