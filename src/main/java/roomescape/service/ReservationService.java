package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationFindResponse;
import roomescape.dto.ReservationSaveRequest;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationTimeService reservationTimeService;
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository, ReservationTimeService reservationTimeService) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeService = reservationTimeService;
    }

    public List<ReservationFindResponse> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(ReservationFindResponse::from)
                .toList();
    }

    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }

    public Reservation save(ReservationSaveRequest request) {
        ReservationTime reservationTime = reservationTimeService.findById(request.timeId());
        Reservation reservation = request.toEntity(reservationTime);
        return reservationRepository.save(reservation);
    }
}
