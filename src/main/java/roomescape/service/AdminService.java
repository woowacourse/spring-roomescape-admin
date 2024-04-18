package roomescape.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDto;
import roomescape.domain.Reservations;

public class AdminService {

    private final Reservations reservations;
    private final AtomicLong index = new AtomicLong(1);

    public AdminService(Reservations reservations) {
        this.reservations = reservations;
    }

    public AdminService() {
        this(new Reservations());
    }

    public List<Reservation> getAllReservations() {
        return reservations.getReservations();
    }

    public Reservation addReservation(ReservationDto reservationDto) {
        Reservation reservation = new Reservation(
                index.getAndIncrement(), reservationDto.name(), reservationDto.date(), reservationDto.time()
        );
        reservations.add(reservation);
        return reservation;
    }

    public void deleteReservation(Long id) {
        System.out.println(reservations.delete(id));
    }
}
