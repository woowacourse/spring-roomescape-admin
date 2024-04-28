package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationResponse> findAllReservation() {
        return reservationRepository.list().stream()
                .map(reservation -> new ReservationResponse(
                                reservation.id(),
                                reservation.name(),
                                reservation.date(),
                                reservation.time()
                        )
                )
                .toList();
    }

    public ReservationResponse addReservation(ReservationRequest reservationRequest) {
        ReservationTime reservationTime = reservationTimeRepository.findById(reservationRequest.timeId());
        Reservation reservation = reservationRepository.insert(new Reservation(
                        null,
                        reservationRequest.name(),
                        reservationRequest.date(),
                        reservationTime
                )
        );

        return new ReservationResponse(reservation.id(), reservation.name(), reservation.date(), reservation.time());
    }

    public void removeReservation(Long id) {
        reservationRepository.delete(id);
    }
}
