package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
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

    public ReservationCreateResponse createReservation(final String name, final String date, final long timeId) {
        final ReservationTime reservationTime = reservationTimeRepository.findById(timeId);
        final Reservation reservation = Reservation.builder()
                                                   .name(name)
                                                   .date(date)
                                                   .time(reservationTime)
                                                   .build();

        final long reservationId = reservationRepository.create(reservation, timeId);
        return ReservationCreateResponse.from(reservationId, reservation);
    }
}
