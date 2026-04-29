package roomescape.domain;

public class Reservation {

    private final Long reservationId;
    private final String customerName;
    private ReservationTime reservationTime;

    public Reservation(Long reservationId, String customerName, ReservationTime reservationTime) {
        this.reservationId = reservationId;
        this.customerName = customerName;
        this.reservationTime = reservationTime;
    }

    public Reservation(String customerName, ReservationTime reservationTime) {
        this(null, customerName, reservationTime);
    }

    public boolean isOverlapping(ReservationTime otherReservationTime) {
        return reservationTime.isOverlapping(otherReservationTime);
    }

    public boolean isSameId(Long reservationId) {
        return this.reservationId != null && this.reservationId.equals(reservationId);
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
