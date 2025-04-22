package roomescape.business.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private final long id;
    private final Customer customer;
    private final LocalDate date;
    private final ReservationTime reservationTime;

    public Reservation(final long id, final Customer customer, final LocalDate date, final ReservationTime reservationTime) {
        validateCustomer(customer);
        validateDate(date);
        validateTime(reservationTime);
        this.id = id;
        this.customer = customer;
        this.date = date;
        this.reservationTime = reservationTime;
    }

    private static void validateDate(final LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("예약 날짜는 null이 될 수 없습니다.");
        }
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("과거 날짜로 예약할 수 없습니다.");
        }
    }

    private static void validateTime(final ReservationTime time) {
        if (time == null) {
            throw new IllegalArgumentException("예약 시간은 null이 될 수 없습니다.");
        }
    }

    private void validateCustomer(final Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("예약자는 null이 될 수 없습니다.");
        }
    }

    public long id() {
        return id;
    }

    public String name() {
        return customer.getName();
    }

    public LocalDate date() {
        return date;
    }

    public ReservationTime reservationTime() {
        return reservationTime;
    }

    public LocalTime startTime() {
        return reservationTime.startTime();
    }
}
