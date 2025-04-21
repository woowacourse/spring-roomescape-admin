package roomescape.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.reservation.service.exception.ReservationNotFoundException;
import roomescape.time.domain.ReservationTime;
import roomescape.time.service.ReservationTimeService;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeService reservationTimeService;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeService reservationTimeService) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeService = reservationTimeService;
    }

    public List<Reservation> findReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(Reservation reservation, Long timeId) {
        ReservationTime reservationTime = reservationTimeService.findById(timeId);
        return reservationRepository.save(reservation, reservationTime);
    }

    public void delete(Long id) {
        Reservation reservation = findReservation(id);
        reservationRepository.deleteById(reservation.getId());
    }

    private Reservation findReservation(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("[ERROR] 예약을 찾을 수 없습니다."));
    }
}
