package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(final ReservationRepository reservationRepository,
                              final ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationResponse createReservation(final ReservationCreateRequest request) {
        final ReservationTime reservationTime = reservationTimeRepository.findById(request.timeId());
        final Reservation reservation = request.toReservation(reservationTime);
        final Long id = reservationRepository.save(reservation);
        return new ReservationResponse(id, reservation.getName(), reservation.getDate(), reservationTime);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public void deleteReservation(final Long id) {
        reservationRepository.deleteById(id);
    }
}
