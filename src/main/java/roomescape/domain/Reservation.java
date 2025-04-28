package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reservation {
    private long id;
    private Person person;
    private LocalDate date;
    private ReservationTime reservationTime;

    public Reservation(final long id,
                       final Person person,
                       final LocalDate date,
                       final ReservationTime reservationTime) {
        validateNullDate(date);
        this.id = id;
        this.person = person;
        this.date = date;
        this.reservationTime = reservationTime;
    }

    public Reservation(final long id, final Reservation reservation, final ReservationTime reservationTime) {
        this.id = id;
        this.person = reservation.getPerson();
        this.date = reservation.getDate();
        this.reservationTime = reservationTime;
    }

    public Reservation(final Person person, final LocalDate date) {
        validateNullDate(date);
        this.id = 0L;
        this.person = person;
        this.date = date;
    }

    private void validateNullDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("예약 날짜는 비어있을 수 없습니다.");
        }
    }

    public boolean isBefore(LocalDateTime today) {
        LocalDateTime reservationDateAndTime = LocalDateTime.of(date, reservationTime.getStartAt());
        return reservationDateAndTime.isBefore(today);
    }

    public String getPersonName() {
        return person.name();
    }

    public Person getPerson() {
        return person;
    }

    public ReservationTime getReservationTime() {
        return reservationTime;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public long getId() {
        return id;
    }

    public boolean isSameId(long id) {
        return this.id == id;
    }
}
