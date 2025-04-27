package roomescape.reservation.domain;

import java.time.LocalDate;
import java.util.Objects;
import roomescape.time.domain.ReservationTime;

public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime reservationTime;

    public Reservation(Long id, String name, LocalDate date, ReservationTime reservationTime) {
        validateName(name);
        validateDate(date);

        this.id = id;
        this.name = name;
        this.date = date;
        this.reservationTime = reservationTime;
    }

    public Reservation(String name, LocalDate date, ReservationTime reservationTime) {
        this(null, name, date, reservationTime);
    }

    public Reservation(Long id, Reservation reservation) {
        this.id = id;
        this.name = reservation.name;
        this.date = reservation.date;
        this.reservationTime = reservation.reservationTime;
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

    private void validateName(String name) {
        if (name == null || name.isBlank() || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name은 빈 값일 수 없습니다.");
        }
    }

    private void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("date는 빈 값일 수 없습니다.");
        }
    }

    public boolean isPast() {
        return date.isBefore(LocalDate.now()) && reservationTime.isPast();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Reservation that)) {
            return false;
        }
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
