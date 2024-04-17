package roomescape.service;

import java.util.List;
import roomescape.domain.Reservation;
import roomescape.domain.Reservations;

public class AdminService {

    private final Reservations reservations;

    public AdminService(Reservations reservations) {
        this.reservations = reservations;
    }

    public AdminService() {
        this(new Reservations());
    }

    public List<Reservation> getAllReservations() {
        return reservations.getReservations();
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public Reservation findReservation(Long id) {
        return reservations.find(id);
    }
}
