package roomescape.entity;

import java.time.LocalDate;

public class Reservation {

    private final Long id;
    private final String customerName;
    private final LocalDate reservationDate;
    private final ReservationTime reservationTime;

    private Reservation(final String customerName, final LocalDate reservationDate, final ReservationTime reservationTime) {
        this(null, customerName, reservationDate, reservationTime);
    }

    private Reservation(final Long id, final String customerName, final LocalDate reservationDate, final ReservationTime reservationTime) {
        this.id = id;
        this.customerName = customerName;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
    }

    public static Reservation of(final String customerName, final LocalDate reservationDate, final ReservationTime reservationTime) {
        return new Reservation(customerName, reservationDate, reservationTime);
    }

    public static Reservation of(final Long id, final String customerName, final LocalDate reservationDate, final ReservationTime reservationTime) {
        return new Reservation(id, customerName, reservationDate, reservationTime);
    }

    public Long getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public ReservationTime getReservationTime() {
        return reservationTime;
    }
}
