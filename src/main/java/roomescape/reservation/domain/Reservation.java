package roomescape.reservation.domain;

import java.time.LocalDateTime;

public class Reservation {

    private final Long id;
    private final String customerName;
    private final LocalDateTime dateTime;

    public Reservation(Long id, String customerName, LocalDateTime dateTime) {
        this.id = id;
        this.customerName = customerName;
        this.dateTime = dateTime;
    }

    public boolean isSameId(final Long id) {
        return this.id.equals(id);
    }

    public Long getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
