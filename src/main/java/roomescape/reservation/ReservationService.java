package roomescape.reservation;

import org.springframework.stereotype.Service;
import roomescape.reservationtime.ReservationTime;
import roomescape.reservationtime.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    private ReservationService(final ReservationTimeRepository reservationTimeRepository, final ReservationRepository reservationRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    public Reservation saveReservation(final ReservationRequest reservationRequest) {
        Long id = reservationRepository.save(reservationRequest.getName(), reservationRequest.getDate(), reservationRequest.getTimeId());
        ReservationTime reservationTime = reservationTimeRepository.findById(reservationRequest.getTimeId());
        return new Reservation(id, reservationRequest.getName(), reservationRequest.getDate(), reservationTime);
    }

    public void deleteReservationTime(Long id) {
        reservationTimeRepository.deleteById(id);
    }
}
