package roomescape.reservation.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import roomescape.reservation.domain.exception.PastReservationException;

public class ReservationDateTime {

    private final LocalDateTime reservationDateTime;

    public ReservationDateTime(LocalDateTime reservationDateTime) {
        this.reservationDateTime = validatePast(reservationDateTime);
    }

    private LocalDateTime validatePast(LocalDateTime reservationTime) {
        LocalDateTime now = LocalDateTime.now();
        if (reservationTime.isBefore(now)) {
            throw new PastReservationException("[ERROR] 예약 불가능한 시간입니다.");
        }
        return reservationTime;
    }

    public LocalDateTime getDateTime() {
        return reservationDateTime;
    }

    public LocalDate getDate() {
        return reservationDateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return reservationDateTime.toLocalTime();
    }

}
