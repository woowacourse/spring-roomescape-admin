package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationCreateResponse;
import roomescape.dto.response.ReservationsResponse;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationSqlRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(final ReservationSqlRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public ReservationsResponse getReservations() {
        return new ReservationsResponse(reservationRepository.findAll());
    }

    public void deleteReservation(final long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    public ReservationCreateResponse createReservation(final ReservationRequest reservationRequest) {
        final Reservation reservation = Reservation.builder()
                                                   .name(reservationRequest.name())
                                                   .date(reservationRequest.date())
                                                   .build();
        final long reservationId = reservationRepository.create(reservation, reservationRequest.timeId());
        return ReservationCreateResponse.from(reservationId, reservation);
    }
}
