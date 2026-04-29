package roomescape.domain;

public class Reservation {

    private final String customerName;
    private final ReservationTime reservationTime;

    public Reservation(String customerName, ReservationTime reservationTime) {
        this.customerName = customerName;
        this.reservationTime = reservationTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public ReservationTime getReservationTime() {
        return reservationTime;
    }
}
