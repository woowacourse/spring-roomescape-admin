package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.mapper.ReservationMapper;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeService timeService;

    public ReservationService(ReservationRepository reservationRepository, TimeService timeService) {
        this.reservationRepository = reservationRepository;
        this.timeService = timeService;
    }

    public ReservationResponse registerReservation(ReservationRequest request) {
        ReservationTime reservationTime = timeService.getTimeById(request.timeId());
        Reservation reservation = ReservationMapper.toDomain(request, reservationTime);
        Long id = reservationRepository.save(reservation);

        return ReservationMapper.toDto(Reservation.assignId(id, reservation));
    }

    public List<ReservationResponse> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return ReservationMapper.toDtos(reservations);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
