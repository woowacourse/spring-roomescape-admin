package roomescape.entity;

import java.time.LocalDate;

public class Reservation {

    private final Long id;
    private final String customerName;
    private final LocalDate reservationDate;
    private final ReservationTime reservationTime;

    private Reservation(final Long id, final String customerName, final LocalDate reservationDate, final ReservationTime reservationTime) {
        validate(customerName, reservationDate);
        this.id = id;
        this.customerName = customerName;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
    }

    public static Reservation of(final String customerName, final LocalDate reservationDate, final ReservationTime reservationTime) {
        return new Reservation(null, customerName, reservationDate, reservationTime);
    }

    public static Reservation of(final Long id, final String customerName, final LocalDate reservationDate, final ReservationTime reservationTime) {
        return new Reservation(id, customerName, reservationDate, reservationTime);
    }

    private void validate(final String customerName, final LocalDate reservationDate) {
        validateCustomerName(customerName);
        validateReservationDate(reservationDate);
    }

    private void validateCustomerName(final String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 예약자 이름은 반드시 입력해야 합니다.");
        }
    }

    private void validateReservationDate(final LocalDate reservationDate) {
        if (reservationDate == null) {
            throw new IllegalArgumentException("[ERROR] 예약일자는 반드시 입력해야 합니다.");
        }
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
