package roomescape.reservation.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import roomescape.reservation.domain.exception.PastReservationException;

public class Reservation {

    private final Long id;
    private final ReserverName reserverName;
    private final LocalDateTime dateTime;

    public Reservation(Long id, String reserverName, LocalDateTime dateTime) {
        this.id = id;
        this.reserverName = new ReserverName(reserverName);
        this.dateTime = validatePast(dateTime);
    }

    private LocalDateTime validatePast(LocalDateTime reservationTime) {
        LocalDateTime now = LocalDateTime.now();
        if (reservationTime.isBefore(now)) {
            throw new PastReservationException("[ERROR] 예약 불가능한 시간입니다.");
        }
        return reservationTime;
    }

    public Reservation(String reserverName, LocalDateTime dateTime) {
        this(null, reserverName, dateTime);
    }

    public Long getId() {
        return id;
    }

    public String getReserverName() {
        return reserverName.getName();
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }
}
