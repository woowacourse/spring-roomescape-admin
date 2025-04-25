package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(
            final ReservationRepository reservationRepository,
            final ReservationTimeRepository reservationTimeRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationResponse createReservation(final ReservationRequest reservationRequest) {
        ReservationTime reservationTime = reservationTimeRepository.findById(reservationRequest.timeId());
        Reservation reservation = new Reservation(
                reservationRequest.name(),
                reservationRequest.date(),
                reservationTime
        );

        Long id = reservationRepository.add(reservation);
        Reservation addedReservation = reservationRepository.findById(id);

        return ReservationResponse.from(addedReservation);
    }

    public List<ReservationResponse> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public void deleteReservation(final Long id) {
        reservationRepository.removeById(id);
    }
}
