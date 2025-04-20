package roomescape;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation {
    private final Long id;
    private final String customerName;
    private final LocalDateTime reservationDateTime;

    public Reservation(Long id, String customerName, LocalDateTime reservationDateTime) {
        this.id = id;
        this.customerName = customerName;
        this.reservationDateTime = reservationDateTime;
    }

    public Reservation(Long id, String customerName, LocalDate reservationDate, LocalTime reservationTime) {
        this(id, customerName, LocalDateTime.of(reservationDate, reservationTime));
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
        return reservationDateTime.toLocalDate();
    }

    public LocalTime getReservationTime() {
        return reservationDateTime.toLocalTime();
    }
}
