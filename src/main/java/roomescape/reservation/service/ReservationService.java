package roomescape.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.CreateReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
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

    public List<ReservationResponse> findAllReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse reserve(CreateReservationRequest createReservationRequest) {
        ReservationTime reservationTime = reservationTimeRepository.findById(createReservationRequest.getTimeId());
        Reservation reservation = ReservationMapper.toReservation(createReservationRequest, reservationTime);
        Reservation saved = reservationRepository.save(reservation);
        return ReservationResponse.from(saved);
    }

    public void cancelReservation(Long id) {
        reservationRepository.delete(id);
    }
}
