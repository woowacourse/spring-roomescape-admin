package roomescape.reservationtime;

public class ReservationTimeRequestDTO {
    private final String startAt;

    public ReservationTimeRequestDTO(String startAt) {
        this.startAt = startAt;
    }

    public String getStartAt() {
        return startAt;
    }
}
