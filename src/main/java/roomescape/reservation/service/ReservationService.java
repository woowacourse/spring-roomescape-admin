package roomescape.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.CreateReservationRequest;
import roomescape.reservation.dto.ReservationResultResponse;
import roomescape.reservation.mapper.ReservationMapper;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.time.domain.ReservationTime;
import roomescape.time.repository.ReservationTimeRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationResultResponse> findAllReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationMapper::toReservationResultDto)
                .toList();
    }

    public ReservationResultResponse reserve(CreateReservationRequest createReservationRequest) {
        ReservationTime reservationTime = reservationTimeRepository.findById(createReservationRequest.getTimeId());
        Reservation reservation = ReservationMapper.toReservation(createReservationRequest, reservationTime);
        Reservation saved = reservationRepository.save(reservation);
        return ReservationMapper.toReservationResultDto(saved);
    }

    public void cancelReservation(Long id) {
        reservationRepository.delete(id);
    }
}
