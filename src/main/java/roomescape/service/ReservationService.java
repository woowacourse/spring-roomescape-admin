package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.model.Reservation;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> findAll() {
        return reservationRepository.readAllReservations();
    }

    public Reservation save(final Reservation reservation) {
        return reservationRepository.createReservation(reservation);
    }

    public void removeById(final Long id) {
        reservationRepository.deleteReservationById(id);
    }
}
