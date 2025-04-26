package roomescape.application.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.application.dto.ReservationRequest;
import roomescape.application.dto.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.domain.Reservations;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationResponse> getReservations() {
        Reservations reservations = reservationRepository.findAll();
        return reservations.getReservations().stream()
                .map(ReservationResponse::new)
                .toList();
    }

    public ReservationResponse saveReservation(ReservationRequest request) {
        ReservationTime reservationTime = reservationTimeRepository.findById(request.timeId());

        Reservation newReservation = request.toReservationWithNullId(reservationTime);
        Reservation savedReservation = reservationRepository.save(newReservation);

        return new ReservationResponse(savedReservation);
    }

    public boolean deleteReservation(Long id) {
        return reservationRepository.deleteById(id);
    }
}
