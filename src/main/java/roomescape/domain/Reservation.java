package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private static final int NAME_MAX_LENGTH = 4;

    private long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(final long id, final String name, final LocalDate date, final ReservationTime time) {
        validateNameLength(name);
        validateDateTime(date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation of(final long id, final String name, final String date, final ReservationTime time) {
        return new Reservation(id, name, LocalDate.parse(date), time);
    }

    public static Reservation of(final String name, final LocalDate date, final ReservationTime time) {
        return new Reservation(0L, name, date, time);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }

    public long getReservationId() {
        return time.getId();
    }

    public void setId(long id) {
        this.id = id;
    }

    private void validateNameLength(final String name) {
        if (name.length() > NAME_MAX_LENGTH) {
            throw new IllegalArgumentException(String.format("예약자의 이름은 %d글자를 초과할 수 없습니다.", NAME_MAX_LENGTH));
        }
    }

    private void validateDateTime(final LocalDate date, final ReservationTime time) {
        if (date.isBefore(LocalDate.now()) || date.isEqual(LocalDate.now()) && time.isBefore(LocalTime.now())) {
            throw new IllegalArgumentException("예약 날짜와 시각은 현재보다 이전일 수 없습니다.");
        }
    }
}
