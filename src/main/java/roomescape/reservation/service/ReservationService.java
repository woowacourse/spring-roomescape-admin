package roomescape.reservation.service;

import org.springframework.stereotype.Service;
import roomescape.reservation.controller.ReservationRequest;
import roomescape.reservation.controller.ReservationResponse;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.reservationtime.controller.ReservationTimeResponse;
import roomescape.reservationtime.repository.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(final ReservationTimeRepository reservationTimeRepository, final ReservationRepository reservationRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponse> getReservations() {
        return reservationRepository.findAll();
    }

    public ReservationResponse saveReservation(final ReservationRequest reservationRequest) {
        Long id = reservationRepository.save(reservationRequest.getName(), reservationRequest.getDate(), reservationRequest.getTimeId());
        ReservationTimeResponse reservationTimeResponse = reservationTimeRepository.findById(reservationRequest.getTimeId());
        return new ReservationResponse(id, reservationRequest.getName(), reservationRequest.getDate(), reservationTimeResponse);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
