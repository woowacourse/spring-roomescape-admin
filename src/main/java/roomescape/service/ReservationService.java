package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationRepository;
import roomescape.domain.Reservation;
import roomescape.exception.InvalidReservationException;

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
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
            return;
        }
        throw new InvalidReservationException("존재하지 않는 id입니다. id: " + id);
    }
}
