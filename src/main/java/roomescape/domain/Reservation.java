package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    
    private final long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime reservationTime;

    public Reservation(final long id, final String name, final LocalDate date, final ReservationTime reservationTime) {
        validateName(name);
        validateDate(date);
        validateTime(reservationTime);
        this.id = id;
        this.name = name;
        this.date = date;
        this.reservationTime = reservationTime;
    }

    private static void validateName(final String name) {
        if (name == null) {
            throw new IllegalArgumentException("예약자명은 null이 될 수 없습니다.");
        }
        if (name.isBlank()) {
            throw new IllegalArgumentException("예약자명은 비어 있을 수 없습니다.");
        }
        if (name.length() > 10) {
            throw new IllegalArgumentException("예약자명은 최대 10자 입니다.");
        }
    }

    private static void validateDate(final LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("예약 날짜는 null이 될 수 없습니다.");
        }
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("과거 날짜로 예약할 수 없습니다.");
        }
    }

    private static void validateTime(final ReservationTime time) {
        if (time == null) {
            throw new IllegalArgumentException("예약 시간은 null이 될 수 없습니다.");
        }
    }

    public long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public LocalDate date() {
        return date;
    }

    public ReservationTime reservationTime() {
        return reservationTime;
    }

    public LocalTime startTime() {
        return reservationTime.startTime();
    }
}
