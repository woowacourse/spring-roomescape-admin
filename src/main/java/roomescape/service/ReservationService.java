package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse findById(long id) {
        Reservation reservation = reservationRepository.findById(id);

        return ReservationResponse.from(reservation);
    }

    public ReservationResponse save(ReservationCreateRequest request) {
        ReservationTime reservationTime = reservationTimeRepository.findById(request.timeId());
        Reservation reservation = request.toReservation(reservationTime);
        Reservation saved = reservationRepository.save(reservation);

        return ReservationResponse.from(saved);
    }

    public void deleteById(long id) {
        reservationRepository.deleteById(id);
    }
}
