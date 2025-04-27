package roomescape.reservation;

import java.time.LocalDate;

public class Reservation {
    private final Long id;
    private final String customerName;
    private final LocalDate reservationDate;
    private final ReservationTime time;

    public Reservation(Long id, String customerName, LocalDate reservationDate, ReservationTime time) {
        validateCustomerName(customerName);
        validateReservationDate(reservationDate);
        validateReservationTime(time);
        this.id = id;
        this.customerName = customerName;
        this.reservationDate = reservationDate;
        this.time = time;
    }

    private void validateCustomerName(String customerName) {
        if (customerName == null || customerName.isBlank()) {
            throw new IllegalArgumentException("유효하지 않은 예약자 이름입니다.");
        }
    }

    private void validateReservationDate(LocalDate reservationDate) {
        if (reservationDate == null) {
            throw new IllegalArgumentException("예약 날짜는 null일 수 없습니다.");
        }
    }

    private void validateReservationTime(ReservationTime time) {
        if (time == null) {
            throw new IllegalArgumentException("예약 시간은 null일 수 없습니다.");
        }
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
        return reservationDate;
    }

    public ReservationTime getReservationTime() {
        return time;
    }

    public long getReservationTimeId() {
        return time.getId();
    }
}
