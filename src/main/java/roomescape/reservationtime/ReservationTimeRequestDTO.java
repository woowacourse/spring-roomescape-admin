package roomescape.reservationtime;

public class ReservationTimeRequestDTO {
    String startAt;

    public ReservationTimeRequestDTO(String startAt) {
        this.startAt = startAt;
    }

    public String getStartAt() {
        return startAt;
    }
}
