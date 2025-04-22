package roomescape.reservation;

import java.time.LocalDate;
import roomescape.time.ReservationTime;

public class Reservation {
    private final Long id;
    private final String customerName;
    private final LocalDate reservationDate;
    private final ReservationTime time;

    public Reservation(Long id, String customerName, LocalDate reservationDate, ReservationTime time) {
        this.id = id;
        this.customerName = customerName;
        this.reservationDate = reservationDate;
        this.time = time;
    }

    public boolean isIdEquals(long id) {
        return this.id == id;
    }

    public long getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public ReservationTime getReservationTime() {
        return time;
    }

    public long getReservationTimeId() {
        return time.getId();
    }
}
