package roomescape.model;

public class Reservation {
    private final Long id;
    private final UserName name;
    private final ReservationDateTime reservationDateTime;


    public Reservation(Long id, UserName name, ReservationDateTime reservationDateTime) {
        this.id = id;
        this.name = name;
        this.reservationDateTime = reservationDateTime;
    }

    public Long getId() {
        return id;
    }

    public UserName getName() {
        return name;
    }

    public ReservationDateTime getReservationDateTime() {
        return reservationDateTime;
    }
}
