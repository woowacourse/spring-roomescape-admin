package roomescape.domain;

import org.springframework.http.HttpStatus;
import roomescape.core.exception.ReservationException;

import java.time.LocalDate;

public class Reservation {

    private long id;
    private String name;
    private LocalDate date;
    private ReservationTime time;

    public Reservation() {
    }

    public Reservation(long id, String name, LocalDate date) {
        this(id, name, date, new ReservationTime());
    }

    private Reservation(String name, LocalDate date, ReservationTime time) {
        this(-1, name, date, time);
    }

    private Reservation(long id, String name, LocalDate date, ReservationTime time) {
        validateNotNull(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validateNotNull(String name, LocalDate date, ReservationTime time) {
        if (name.isEmpty()) {
            throw new ReservationException(HttpStatus.BAD_REQUEST, "이름은 비어있을 수 없습니다");
        }
        if (date == null) {
            throw new ReservationException(HttpStatus.BAD_REQUEST, "예약날짜는 비어있을 수 없습니다");
        }
        if (time == null) {
            throw new ReservationException(HttpStatus.BAD_REQUEST, "예약시간은 비어있을 수 없습니다.");
        }
    }

    public static Reservation from(String name, LocalDate date, long timeId) {
        ReservationTime reservationTime = new ReservationTime(timeId);
        return new Reservation(name, date, reservationTime);
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

    public void setTime(ReservationTime time) {
        this.time = time;
    }
}
