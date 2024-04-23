package roomescape.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.reservationtime.ReservationTime;
import roomescape.reservationtime.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    public Reservation saveReservation(final ReservationRequest reservationRequest) {
        Long id = reservationRepository.save(reservationRequest.getName(), reservationRequest.getDate(), reservationRequest.getTimeId());
        ReservationTime reservationTime = reservationTimeRepository.findById(reservationRequest.getTimeId());
        return new Reservation(id, reservationRequest.getName(), reservationRequest.getDate(), reservationTime);
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    public void deleteReservationTime(Long id) {
        reservationTimeRepository.deleteById(id);
    }
}
