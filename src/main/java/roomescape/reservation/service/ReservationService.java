package roomescape.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservation.controller.request.CreateReservationRequest;
import roomescape.reservation.controller.response.FindReservationResponse;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.reservationtime.domain.ReservationTime;
import roomescape.reservationtime.repository.ReservationTimeRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(final ReservationRepository reservationRepository,
                              final ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<FindReservationResponse> getReservations() {
        return reservationRepository.findAll().stream()
                .map(FindReservationResponse::of)
                .toList();
    }

    public Long createReservation(final CreateReservationRequest createReservationRequest) {
        ReservationTime reservationTime = reservationTimeRepository.findById(createReservationRequest.timeId());
        return reservationRepository.save(createReservationRequest.toDomain(reservationTime));
    }

    public void deleteReservation(final Long id) {
        reservationRepository.deleteById(id);
    }
}
