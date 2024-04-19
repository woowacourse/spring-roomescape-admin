package roomescape.service;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationDto;
import roomescape.domain.Reservations;

import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class AdminService {

    private final Reservations reservations;
    private final AtomicLong index = new AtomicLong(1);


    public AdminService(Reservations reservations) {
        this.reservations = reservations;
    }

    public AdminService() {
        this(new Reservations());
    }

    public Set<Reservation> getAllReservations() {
        return reservations.getReservations();
    }

    public Reservation reserve(ReservationDto reservationDto) {
        Reservation reservation = createReservation(reservationDto);
        addReservation(reservation);
        return reservation;
    }

    public Reservation findReservation(Long id) {
        return reservations.find(id);
    }

    public void deleteReservation(Long id) {
        reservations.delete(id);
    }

    private void addReservation(Reservation reservation) {
        reservations.add(reservation.getId(), reservation);
    }

    private Reservation createReservation(ReservationDto reservationDto) {
        return new Reservation(index.getAndIncrement(), reservationDto);
    }

}
