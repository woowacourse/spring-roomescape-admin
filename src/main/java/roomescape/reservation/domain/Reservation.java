package roomescape.reservation.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import roomescape.reservationtime.domain.ReservationTime;

public class Reservation {
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(final String name, final LocalDate date, final ReservationTime time) {
        validateDateTime(date, time.getStartAt());
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
}
