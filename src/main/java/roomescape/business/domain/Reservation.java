package roomescape.business.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Reservation {

    private final Customer customer;
    private final LocalDate date;
    private final ReservationTime reservationTime;

    public Reservation(final Customer customer, final LocalDate date, final ReservationTime reservationTime) {
        Objects.requireNonNull(customer, "예약자는 null이 될 수 없습니다.");
        Objects.requireNonNull(date, "예약 날짜는 null이 될 수 없습니다.");
        Objects.requireNonNull(reservationTime, "예약 시간은 null이 될 수 없습니다.");
        validateDateIsFutureOrPresent(date);
        this.customer = customer;
        this.date = date;
        this.reservationTime = reservationTime;
    }

    private static void validateDateIsFutureOrPresent(final LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("과거 날짜로 예약할 수 없습니다.");
        }
    }

    public String customerName() {
        return customer.name();
    }

    public LocalDate date() {
        return date;
    }

    public ReservationTime reservationTime() {
        return reservationTime;
    }
}
