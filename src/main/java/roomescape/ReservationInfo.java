package roomescape;

public class ReservationInfo {
    private Long id;
    private String name;
    private String date;
    private String time;

    public ReservationInfo(Long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
