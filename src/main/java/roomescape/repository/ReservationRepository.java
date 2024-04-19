package roomescape.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.dto.Reservation;

@Repository
public class ReservationRepository {
    private final ReservationDao reservationDao;

    public ReservationRepository(final ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    /*
    public Reservation saveReservation(final ReservationRequest reservationRequest) {
        long id = index.incrementAndGet();
        Reservation reservation = new Reservation(
                id,
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.time()
        );
        reservations.put(id, reservation);
        return reservation;
    }

    public void deleteReservation(final long id) {
        reservations.remove(id);
    }

    public void deleteAll() {
        reservations.clear();
    }*/

    public List<Reservation> getAllReservations() {
        return reservationDao.getReservations();
    }
}
