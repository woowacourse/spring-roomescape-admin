package roomescape.reservation;

import java.time.LocalDate;
import java.util.Objects;
import roomescape.reservationtime.ReservationTime;

public class Reservation {
    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime reservationTime;

    public Reservation(String name, LocalDate date, ReservationTime reservationTime) {
        validate(name, date, reservationTime);
        this.id = null;
        this.name = name;
        this.date = date;
        this.reservationTime = reservationTime;
    }

    public Reservation(Long id, String name, LocalDate date, ReservationTime reservationTime) {
        validate(name, date, reservationTime);
        this.id = id;
        this.name = name;
        this.date = date;
        this.reservationTime = reservationTime;
    }

    private void validate(String name, LocalDate date, ReservationTime reservationTime) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 빈 칸일 수 없습니다.");
        }
        if (date == null) {
            throw new IllegalArgumentException("예약 날짜가 존재해야합니다.");
        }
        if (reservationTime == null) {
            throw new IllegalArgumentException("예약 시간이 존재해야합니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getReservationTime() {
        return reservationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reservation that = (Reservation) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName())
                && Objects.equals(getDate(), that.getDate()) && Objects.equals(getReservationTime(),
                that.getReservationTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDate(), getReservationTime());
    }
}
