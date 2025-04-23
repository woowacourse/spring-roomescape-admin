package roomescape.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservation.controller.request.ReservationCreateRequest;
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

    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    public Reservation create(ReservationCreateRequest request) {
        ReservationTime reservationTime = reservationTimeService.getReservationTime(request.timeId());
        Reservation reservation = new Reservation(request.name(), request.date(), reservationTime);
        return reservationRepository.save(reservation);
    }

    public void deleteById(Long id) {
        Reservation reservation = getReservation(id);
        reservationRepository.deleteById(reservation.getId());
    }

    private Reservation getReservation(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("[ERROR] 예약을 찾을 수 없습니다."));
    }
}
