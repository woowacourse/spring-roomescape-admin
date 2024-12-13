package roomescape.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.dto.ReservationRequest;
import roomescape.service.dto.ReservationResponse;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository timeRepository;

    @Transactional
    public ReservationResponse create(ReservationRequest reservationRequest) {
        ReservationTime time = timeRepository.findById(reservationRequest.timeId()).orElseThrow();
        Reservation reservation = new Reservation(reservationRequest.name(), reservationRequest.date(), time);
        Reservation savedReservation = reservationRepository.save(reservation);

        return new ReservationResponse(savedReservation);
    }

    public ReservationResponse findOne(long id) {
        Reservation found = reservationRepository.findById(id).orElseThrow();

        return new ReservationResponse(found);
    }

    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::new)
                .toList();
    }

    @Transactional
    public void remove(Long id) {
        reservationRepository.delete(id);
    }
}
