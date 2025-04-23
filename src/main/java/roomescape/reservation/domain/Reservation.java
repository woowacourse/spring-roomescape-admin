package roomescape.reservation.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import roomescape.reservation.domain.exception.PastReservationException;
import roomescape.time.domain.ReservationTime;

public class Reservation {

    private final Long id;
    private final ReserverName reserverName;
    private final ReservationDate reservationDate;
    private ReservationTime reservationTime;

    public Reservation(Long id, String reserverName, LocalDate date, ReservationTime reservationTime) {
        this.id = id;
        this.reserverName = new ReserverName(reserverName);
        this.reservationDate = new ReservationDate(date);
        this.reservationTime = reservationTime;
    }

    public Reservation(String reserverName, LocalDate date, ReservationTime reservationTime) {
        this(null, reserverName, date, reservationTime);
        validatePast();
    }

    private void validatePast() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reservationDateTime = LocalDateTime.of(reservationDate.getDate(), reservationTime.getStartAt());

        if (reservationDateTime.isBefore(now) || reservationDateTime.isEqual(now)) {
            throw new PastReservationException("[ERROR] 현재 시간 이후로 예약할 수 있습니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getReserverName() {
        return reserverName.getName();
    }

    public LocalDate getDate() {
        return reservationDate.getDate();
    }

    public LocalTime getStartAt() {
        return reservationTime.getStartAt();
    }

    public ReservationTime getReservationTime() {
        return reservationTime;
    }

    public Long getTimeId() {
        return reservationTime.getId();
    }

}
