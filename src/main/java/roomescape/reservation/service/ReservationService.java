package roomescape.reservation.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.mapper.ReservationMapper;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.reservationtime.domain.ReservationTime;
import roomescape.reservationtime.repository.ReservationTimeRepository;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public List<ReservationResponse> findAllReservations() {
        List<Reservation> reservations = reservationRepository.findAllReservations();

        return reservations.stream()
                .map(ReservationMapper::toResponse)
                .toList();
    }

    public ReservationResponse saveReservation(ReservationRequest reservationRequest) {
        ReservationTime time = reservationTimeRepository.findById(reservationRequest.timeId());
        Reservation reservation = ReservationMapper.toEntity(reservationRequest, time);
        Reservation createdReservation = reservationRepository.saveReservation(reservation);
        return ReservationMapper.toResponse(createdReservation);
    }

    public int deleteById(Long id) {
        return reservationRepository.deleteById(id);
    }
}
