package roomescape.reservationtime;

public class ReservationTimeResponseDTO {
    private final Long id;
    private final String startAt;

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
