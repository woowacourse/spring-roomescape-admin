package roomescape.reservation.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.time.domain.ReservationTime;

public class Reservation {

    private final Long id;
    private final ReserverName reserverName;
    private final ReservationDate reservationDate;
    private ReservationTime reservationTime;

    public Reservation(Long id, String reserverName, LocalDate date, ReservationTime reservationTime) {
        this.id = id;
        this.reserverName = new ReserverName(reserverName);
        this.reservationDate = new ReservationDate(date);
        this.reservationTime = reservationTime;
    }

    public Reservation(String reserverName, LocalDate date, ReservationTime reservationTime) {
        this(null, reserverName, date, reservationTime);
    }

    public Long getId() {
        return id;
    }

    public String getReserverName() {
        return reserverName.getName();
    }

    public LocalDate getDate() {
        return reservationDate.getDate();
    }

    public LocalTime getStartAt() {
        return reservationTime.getStartAt();
    }

    public ReservationTime getReservationTime() {
        return reservationTime;
    }

    public Long getTimeId() {
        return reservationTime.getId();
    }

    public void setReservationTime(ReservationTime reservationTime) {
        this.reservationTime = reservationTime;
    }
}
