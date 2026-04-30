package roomescape.reservationtime;

public class ReservationTimeResponseDTO {
    Long id;
    String startAt;

    public ReservationTimeResponseDTO(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
