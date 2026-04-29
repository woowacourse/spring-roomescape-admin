package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.response.ReservationResponse;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository, ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }


    public List<Reservation> findAllReservations() {
        return reservationRepository.findAllReservations();
    }

    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }

    public ReservationTime findTime(Long id) {
        return reservationTimeRepository.findById(id);
    }

    public Reservation addReservation(Reservation reservation) {
        return reservationRepository.addReservation(reservation);
    }
}
