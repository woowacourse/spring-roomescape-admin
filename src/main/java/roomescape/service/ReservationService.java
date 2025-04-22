package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.mapper.ReservationMapper;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public ReservationResponse registerReservation(ReservationRequest request) {
        ReservationTime reservationTime = timeRepository.findById(request.timeId());
        Reservation reservation = ReservationMapper.toDomain(request, reservationTime);
        Long id = reservationRepository.save(reservation);

        return ReservationMapper.toDto(Reservation.withId(id, reservation));
    }

    public List<ReservationResponse> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return ReservationMapper.toDtos(reservations);
    }

    public void cancelReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
