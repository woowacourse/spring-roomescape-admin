package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final TimeService timeService;

    public ReservationService(ReservationRepository reservationRepository, TimeService timeService) {
        this.reservationRepository = reservationRepository;
        this.timeService = timeService;
    }


    public Reservation add(ReservationRequest request) {
        ReservationTime time = timeService.findById(request.getTimeId());

        Reservation reservation = new Reservation(request.getName(), request.getDate(), time);
        return reservationRepository.add(reservation);
    }


    public List<Reservation> findAllReservations() {
        return reservationRepository.findAllReservations();
    }


    public void deleteReservation(Long id) {
        reservationRepository.remove(id);
    }
}
