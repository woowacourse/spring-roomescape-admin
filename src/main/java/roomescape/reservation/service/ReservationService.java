package roomescape.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationResultResponse;
import roomescape.reservation.mapper.ReservationMapper;
import roomescape.reservation.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResultResponse> findAllReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationMapper::toReservationResultDto)
                .toList();
    }

    public ReservationResultResponse reserve(Reservation reservation) {
        Reservation saved = reservationRepository.save(reservation);
        return ReservationMapper.toReservationResultDto(saved);
    }

    public void cancelReservation(Long id) {
        reservationRepository.delete(id);
    }
}
