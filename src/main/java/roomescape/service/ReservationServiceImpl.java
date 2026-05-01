package roomescape.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequest;
import roomescape.repository.ReservationRepository;

import java.util.List;

@Primary
@Service
public class ReservationServiceImpl implements ReservationService{
    private final ReservationRepository reservationRepository;
    private final TimeService timeService;

    public ReservationServiceImpl(ReservationRepository reservationRepository, TimeService timeService) {
        this.reservationRepository = reservationRepository;
        this.timeService = timeService;
    }

    @Override
    public Reservation add(ReservationRequest request) {
        Time time = timeService.findById(request.getTimeId());

        Reservation reservation = new Reservation(request.getName(), request.getDate(), time);
        return reservationRepository.add(reservation);
    }

    @Override
    public List<Reservation> findAllReservations() {
        return reservationRepository.findAllReservations();
    }

    @Override
    public void deleteReservation(Long id) {
        reservationRepository.remove(id);
    }
}
