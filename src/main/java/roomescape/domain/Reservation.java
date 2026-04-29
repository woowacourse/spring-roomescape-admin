package roomescape.domain;

public class Reservation {

    private final Long reservationId;
    private final String customerName;
    private final ReservationTime reservationTime;

    public Reservation(Long reservationId, String customerName, ReservationTime reservationTime) {
        this.reservationId = reservationId;
        this.customerName = customerName;
        this.reservationTime = reservationTime;
    }

    public Reservation(String customerName, ReservationTime reservationTime) {
        this(null, customerName, reservationTime);
    }

    public Long getReservationId() {
        return reservationId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public ReservationTime getReservationTime() {
        return reservationTime;
    }
}
