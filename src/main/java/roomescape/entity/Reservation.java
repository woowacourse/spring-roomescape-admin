package roomescape.entity;

import java.time.LocalDateTime;

public class Reservation {

    private final Long id;
    private final String customerName;
    private final LocalDateTime reservationDateTime;

    public Reservation(final Long id, final String customerName, final LocalDateTime reservationDateTime) {
        validateCustomerName(customerName);
        validateReservationDateTime(reservationDateTime);
        this.id = id;
        this.customerName = customerName;
        this.reservationDateTime = reservationDateTime;
    }

    private void validateCustomerName(final String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 예약자 이름은 반드시 입력해야 합니다.");
        }
    }

    private void validateReservationDateTime(final LocalDateTime reservationDateTime) {
        if (reservationDateTime == null) {
            throw new IllegalArgumentException("[ERROR] 예약일자와 시간은 반드시 입력해야 합니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public LocalDateTime getReservationDateTime() {
        return reservationDateTime;
    }
}
