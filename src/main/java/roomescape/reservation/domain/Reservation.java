package roomescape.reservation.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    public static final LocalTime START_RESERVATION_TIME = LocalTime.of(10, 0);
    public static final LocalTime LAST_RESERVATION_TIME = LocalTime.of(23, 0);

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    private Reservation(final Long id, final String name, final LocalDate date, final LocalTime time) {
        validateTime(time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation createWithId(Long id, String name, LocalDate date, LocalTime time) {
        return new Reservation(id, name, date, time);
    }

    public static Reservation createWithoutId(String name, LocalDate date, LocalTime time) {
        return new Reservation(null, name, date, time);
    }

    private void validateTime(final LocalTime time) {
        if (time.isBefore(START_RESERVATION_TIME) || time.isAfter(LAST_RESERVATION_TIME)) {
            throw new IllegalArgumentException("예약할 수 없는 시간입니다.");
        }
    }

    public boolean isSaved() {
        return id == null;
    }

    public Long getId() {
        if (isSaved()) {
            throw new IllegalStateException("저장되지 않은 예약은 예약 번호가 존재하지 않습니다");
        }
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
