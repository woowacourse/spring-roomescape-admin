package roomescape.reservation.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.mapper.ReservationMapper;
import roomescape.reservation.repository.ReservationRepository;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAllReservations();
    }

    public ReservationResponse saveReservation(ReservationRequest reservationRequest) {
        Reservation reservation = ReservationMapper.toEntity(reservationRequest);
        Reservation createdReservation = reservationRepository.saveReservation(reservation);
        return ReservationMapper.toResponse(createdReservation);
    }

    public int deleteById(Long id) {
        return reservationRepository.deleteById(id);
    }
}
