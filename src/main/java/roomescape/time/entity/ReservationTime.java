package roomescape.time.entity;

public class ReservationTime {

    private Long id;
    private final String startAt;

    public ReservationTime(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTime create(String startAt) {
        return new ReservationTime(null, startAt);
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }

    public ReservationTime toEntity(long id) {
        return new ReservationTime(id, startAt);
    }

}
