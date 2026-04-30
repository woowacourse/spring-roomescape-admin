package roomescape.domain;

public class ReservationTime {
    private Long id;
    private String startAt;

    public ReservationTime() {
    }

    public ReservationTime(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public ReservationTime(String startAt) {
        this.startAt = startAt;
    }

    public static ReservationTime toEntity(ReservationTime reservationTime, long id) {
        return new ReservationTime(id, reservationTime.startAt);
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
