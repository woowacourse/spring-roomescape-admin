package roomescape.reservation.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.entity.ReservationTime;
import roomescape.common.exception.EntityNotFoundException;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.reservation.repository.ReservationTimeRepository;
import roomescape.reservation.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @Override
    public List<ReservationResponse> getAll() {
        List<Reservation> reservations = reservationRepository.findAll();

        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @Override
    public ReservationResponse save(ReservationRequest request) {
        Long timeId = request.timeId();
        ReservationTime reservationTime = reservationTimeRepository.findById(timeId)
                .orElseThrow(() -> new EntityNotFoundException("reservationsTime not found id =" + timeId));

        Reservation reservation = Reservation.withoutId(request.name(), request.date(), reservationTime);

        Reservation saved = reservationRepository.save(reservation);

        return ReservationResponse.from(saved);
    }

    @Override
    public void delete(Long id){
        reservationRepository.deleteById(id);
    }
}
