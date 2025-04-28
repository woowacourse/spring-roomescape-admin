package roomescape.reservation.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import roomescape.reservationtime.domain.ReservationTime;

public class Reservation {
    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(final Long id, final String name, final LocalDate date, final ReservationTime time) {
        validateDateTime(date, time.getStartAt());
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validateDateTime(LocalDate date, LocalTime time) {
        if (LocalDateTime.of(date, time).isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("예약 시간이 현재 시간보다 이전일 수 없습니다.");
        }
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

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reservation that = (Reservation) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName())
                && Objects.equals(getDate(), that.getDate()) && Objects.equals(getTime(),
                that.getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDate(), getTime());
    }
}
