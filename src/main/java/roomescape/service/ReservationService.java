package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationRepository;
import roomescape.domain.Reservation;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation create(final Reservation reservation) {
        return reservationRepository.save(reservation);
    }


    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }


    public void deleteById(final long id) {
        reservationRepository.deleteById(id);
    }
}
