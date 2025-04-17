package roomescape;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation {
    private final long id;
    private String customerName;
    private LocalDateTime reservationDateTime;

    public Reservation(long id, String customerName, LocalDateTime reservationDateTime) {
        this.id = id;
        this.customerName = customerName;
        this.reservationDateTime = reservationDateTime;
    }

    public Reservation(long id, String customerName, LocalDate reservationDate, LocalTime reservationTime) {
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
