package roomescape.domain;

public class Reservation {
    private final Long id;
    private final Name name;
    private final ReservationDate date;
    private final ReservationTime time;

    public Reservation(Long id, Name name, ReservationDate date, ReservationTime time) {
        if (id != null && id <= 0) {
            throw new IllegalArgumentException("[ERROR] 예약 ID는 양수여야 합니다.");
        }

        if (name == null) {
            throw new IllegalArgumentException("[ERROR] 예약자 이름은 null일 수 없습니다.");
        }

        if (date == null) {
            throw new IllegalArgumentException("[ERROR] 예약 날짜는 null일 수 없습니다.");
        }

        if (time == null) {
            throw new IllegalArgumentException("[ERROR] 예약 시간은 null일 수 없습니다.");
        }

        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.value();
    }

    public Name name() {
        return name;
    }

    public String getDate() {
        return date.value().toString();
    }

    public ReservationDate date() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }

    public ReservationTime time() {
        return time;
    }
}
