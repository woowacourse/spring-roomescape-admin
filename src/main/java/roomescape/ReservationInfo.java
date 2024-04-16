package roomescape;

public class ReservationInfo {
    private Long id;
    private String name;
    private String date;
    private String time;

    public ReservationInfo() {

    }

    public ReservationInfo(Long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public ReservationInfo(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationInfo toEntity(ReservationInfo reservationInfo, Long id) {
        return new ReservationInfo(id, reservationInfo.name, reservationInfo.date, reservationInfo.time);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "ReservationInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
