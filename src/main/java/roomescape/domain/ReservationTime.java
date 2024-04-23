package roomescape.domain;

public class ReservationTime {

    private Long id;
    private String time;

    public ReservationTime(String time) {
        this.time = time;
    }

    public ReservationTime(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }
}
