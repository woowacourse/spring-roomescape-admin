package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeService reservationTimeService;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeService reservationTimeService) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeService = reservationTimeService;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation reserve(ReservationCreateRequest request) {
        ReservationTime reservationTime = reservationTimeService.find(request.getTimeId());

        Reservation reservation = Reservation.of(request.getName(), request.getDate(), reservationTime);

        return reservationRepository.save(reservation);
    }

    public void cancel(long reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}
