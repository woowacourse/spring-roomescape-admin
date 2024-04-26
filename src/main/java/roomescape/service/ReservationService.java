package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.response.ReservationCreateResponse;
import roomescape.dto.response.ReservationsResponse;
import roomescape.exception.reservation.time.NotExistReservationTimeException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationSqlRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.request.ReservationCreateRequestInService;

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

    public ReservationCreateResponse createReservation(final ReservationCreateRequestInService request) {
        final long reservationTimeId = request.timeId();
        final ReservationTime reservationTime = reservationTimeRepository.findById(reservationTimeId)
                                                                         .orElseThrow(() -> new NotExistReservationTimeException(reservationTimeId));
        final Reservation reservation = Reservation.builder()
                                                   .name(request.name())
                                                   .date(request.date())
                                                   .time(reservationTime)
                                                   .build();

        final long reservationId = reservationRepository.create(reservation, reservationTimeId);
        return ReservationCreateResponse.from(reservationId, reservation);
    }
}
