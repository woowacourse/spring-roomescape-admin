package roomescape.domain;

public class Reservation {

    private final Long id;
    private final String name;
    private final String date;
    private final ReservationTime time;

    public Reservation(final Long id, final String name, final String date, final ReservationTime time) {
        validate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validate(final String name, final String date, final ReservationTime time) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("예약자 이름은 비어있을 수 없습니다.");
        }
        if (date == null || date.isBlank()) {
            throw new IllegalArgumentException("예약 날짜는 비어있을 수 없습니다.");
        }
        if (time == null) {
            throw new IllegalArgumentException("예약 시간은 비어있을 수 없습니다.");
        }
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

    public ReservationTime getTime() {
        return time;
    }
}
