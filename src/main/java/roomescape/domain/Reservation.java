package roomescape.domain;

import org.springframework.http.HttpStatus;
import roomescape.core.exception.ReservationException;

import java.time.LocalDate;

public class Reservation {

    private int id;
    private String name;
    private LocalDate date;
    private ReservationTime time;

    public Reservation() {
    }

    public static Reservation from(String name, LocalDate date, int timeId) {
        ReservationTime reservationTime = new ReservationTime();
        reservationTime.setId(timeId);
        return new Reservation(name, date, reservationTime);
    }

    public Reservation(String name, LocalDate date, ReservationTime time) {
        validateNotNull(name, date, time);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ReservationTime getTime() {
        return time;
    }

    public void setTime(ReservationTime time) {
        this.time = time;
    }
}
