package roomescape.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.CreateReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.dto.ReservationsResponse;
import roomescape.reservation.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public ReservationsResponse findAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        return ReservationsResponse.from(reservations);
    }

    public ReservationResponse create(CreateReservationRequest createReservationRequest) {
        Long id = reservationRepository.generateId();
        Reservation reservation = reservationRepository.save(
                new Reservation(id, createReservationRequest.name(), createReservationRequest.date(),
                        createReservationRequest.time()));
        return ReservationResponse.from(reservation);
    }

    public ReservationResponse delete(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 예외입니다."));
        reservationRepository.delete(id);
        return ReservationResponse.from(reservation);
    }
}
