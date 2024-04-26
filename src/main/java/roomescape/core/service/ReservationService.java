package roomescape.core.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.core.domain.Reservation;
import roomescape.core.domain.ReservationTime;
import roomescape.core.repository.ReservationRepository;
import roomescape.core.repository.ReservationTimeRepository;
import roomescape.core.service.dto.ReservationServiceRequest;
import roomescape.core.service.dto.ReservationServiceResponse;

@Service
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository, ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationServiceResponse create(ReservationServiceRequest reservationServiceRequest) {
        ReservationTime reservationTime = reservationTimeRepository.findById(reservationServiceRequest.timeId())
                .orElseThrow(() -> new IllegalArgumentException("Time not found"));

        Reservation reservation = reservationServiceRequest.toReservation(reservationTime);

        Reservation savedReservation = reservationRepository.save(reservation);

        return ReservationServiceResponse.from(savedReservation);
    }

    @Transactional(readOnly = true)
    public List<ReservationServiceResponse> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();

        return reservations.stream()
                .map(ReservationServiceResponse::from)
                .toList();
    }

    public void delete(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        reservationRepository.delete(reservation);
    }
}
