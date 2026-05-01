package roomescape.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequest;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.util.List;

@Primary
@Service
public class ReservationServiceImpl implements ReservationService{
    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository; // Time 조회를 위해 추가!


    public ReservationServiceImpl(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    @Override
    public Reservation add(ReservationRequest request) {
        Time time = timeRepository.findById(request.getTimeId());

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
