package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationCreateResponse;
import roomescape.dto.response.ReservationsResponse;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationSqlRepository;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(final ReservationSqlRepository reservationRepository, final ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationsResponse getReservations() {
        return new ReservationsResponse(reservationRepository.findAll());
    }

    public void deleteReservation(final long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    public ReservationCreateResponse createReservation(final ReservationRequest reservationRequest) {
        final ReservationTime reservationTime = reservationTimeRepository.findById(reservationRequest.timeId());
        final Reservation reservation = Reservation.builder()
                                                   .name(reservationRequest.name())
                                                   .date(reservationRequest.date())
                                                   .time(reservationTime)
                                                   .build();

        final long reservationId = reservationRepository.create(reservation, reservationRequest.timeId());
        return ReservationCreateResponse.from(reservationId, reservation);
    }
}
