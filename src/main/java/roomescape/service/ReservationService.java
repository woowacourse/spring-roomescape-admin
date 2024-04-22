package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationCreateResponse;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationSqlRepository;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(final ReservationSqlRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    public void deleteReservation(final long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    public ReservationCreateResponse createReservation(final ReservationRequest reservationRequest) {
        final Reservation reservation = Reservation.builder()
                                                   .name(reservationRequest.name())
                                                   .date(reservationRequest.date())
                                                   .time(reservationRequest.time())
                                                   .build();
        final long reservationId = reservationRepository.create(reservation);
        return ReservationCreateResponse.from(reservationId, reservation);
    }
}
