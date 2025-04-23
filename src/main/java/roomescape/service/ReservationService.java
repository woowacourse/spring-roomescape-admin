package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.repository.ReservationRepository;
import roomescape.service.dto.ReservationResponse;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponse> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::toDto)
                .toList();
    }

    public ReservationResponse addReservation(final ReservationCreateRequest reservationCreateRequest) {
        Long id = reservationRepository.add(reservationCreateRequest.toReservation());
        return ReservationResponse.toDto(reservationRepository.findById(id));
    }

    public ReservationResponse getReservationById(final Long id) {
        return ReservationResponse.toDto(reservationRepository.findById(id));
    }

    public void deleteReservationById(final Long id) {
        reservationRepository.deleteById(id);
    }
}
