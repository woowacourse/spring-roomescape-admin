package roomescape.reservation.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.time.domain.ReservationTime;

public class Reservation {

    private final Long id;
    private final ReserverName reserverName;
    private final ReservationDate reservationDate;
    private ReservationTime reservationTime;

    public Reservation(Long id, String reserverName, LocalDate date, LocalTime time) {
        this.id = id;
        this.reserverName = new ReserverName(reserverName);
        this.reservationDate = new ReservationDate(date);
        this.reservationTime = new ReservationTime(time);
    }

    public Reservation(String reserverName, LocalDate date, LocalTime time) {
        this(null, reserverName, date, time);
    }

//    private LocalDateTime validatePast(LocalDateTime reservationTime, LocalDateTime now) {
//        if (reservationTime.isBefore(now)) {
//            throw new PastReservationException("[ERROR] 예약 불가능한 시간입니다.");
//        }
//        return reservationTime;
//    }

    public Long getId() {
        return id;
    }

    public String getReserverName() {
        return reserverName.getName();
    }

    public LocalDate getDate() {
        return reservationDate.getDate();
    }

    public LocalTime getTime() {
        return reservationTime.getStartAt();
    }

    public Long getTimeId() {
        return reservationTime.getId();
    }

    public void setReservationTime(ReservationTime reservationTime) {
        this.reservationTime = reservationTime;
    }
}
