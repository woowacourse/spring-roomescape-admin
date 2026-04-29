package roomescape.step4.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.step4.domain.Reservation;
import roomescape.step4.domain.ReservationTime;
import roomescape.step4.dto.ReservationRequest;
import roomescape.step4.repository.ReservationRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeService reservationTimeService;

    public ReservationService(ReservationRepository reservationRepository, ReservationTimeService reservationTimeService) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeService = reservationTimeService;
    }

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    @Transactional
    public Reservation saveReservation(ReservationRequest request) {
        ReservationTime reservationTime = reservationTimeService.findReservationTime(request.timeId());

        Reservation reservation = new Reservation(
                null,
                request.name(),
                request.date(),
                reservationTime
        );
        return reservationRepository.save(reservation);
    }

    @Transactional
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
