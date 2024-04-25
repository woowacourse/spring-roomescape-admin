package roomescape.domain;

public class ReservationTime {

    private Long id;
    private String startAt;

    private ReservationTime() {
    }

    public ReservationTime(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public ReservationTime(String startAt) {
        this(null, startAt);
    }

    public static ReservationTime toEntity(Long id, ReservationTime request) {
        return new ReservationTime(id, request.getStartAt());
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
