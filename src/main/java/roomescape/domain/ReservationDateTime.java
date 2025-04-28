package roomescape.domain;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class ReservationDateTime {
    private final ReservationDate reservationDate;
    private final ReservationTime reservationTime;

    public ReservationDateTime(
            final ReservationDate reservationDate,
            final ReservationTime reservationTime,
            final Clock clock
    ) {
        this.reservationDate = Objects.requireNonNull(reservationDate, "예약 날짜는 null일 수 없습니다.");
        this.reservationTime = Objects.requireNonNull(reservationTime, "예약 시간은 null일 수 없습니다.");
        if (!this.isAfter(LocalDateTime.now(clock))) {
            throw new IllegalArgumentException("예약 일시는 현재 이후여야 합니다.");
        }
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReservationDateTime dateTime)) {
            return false;
        }
        return Objects.equals(reservationDate, dateTime.reservationDate) && Objects.equals(
                reservationTime, dateTime.reservationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationDate, reservationTime);
    }
}
