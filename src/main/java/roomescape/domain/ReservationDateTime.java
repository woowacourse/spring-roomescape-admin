package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class ReservationDateTime {
    private final ReservationDate reservationDate;
    private final ReservationTime reservationTime;

    public ReservationDateTime(final ReservationDate reservationDate, final ReservationTime reservationTime) {
        this.reservationDate = Objects.requireNonNull(reservationDate, "예약 날짜는 null일 수 없습니다.");
        this.reservationTime = Objects.requireNonNull(reservationTime, "예약 시간은 null일 수 없습니다.");
    }

    public LocalDate date() {
        return reservationDate.date();
    }

    public LocalTime time() {
        return reservationTime.time();
    }

    public ReservationTime reservationTime() {
        return reservationTime;
    }

    public ReservationDate reservationDate() {
        return reservationDate;
    }

    public boolean isAfter(final LocalDateTime now) {
        return LocalDateTime.of(reservationDate.date(), reservationTime.time()).isAfter(now);
    }
}
