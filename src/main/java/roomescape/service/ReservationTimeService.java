package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.dto.response.ReservationTimeCreateResponse;
import roomescape.dto.response.ReservationTimesResponse;
import roomescape.exception.reservation.time.NotExistReservationTimeException;
import roomescape.exception.reservation.time.ReservationExistInReservationTimeException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;
    private final ReservationRepository reservationRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository, ReservationRepository reservationRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
        this.reservationRepository = reservationRepository;
    }

    public ReservationTimesResponse getReservationTimes() {
        return new ReservationTimesResponse(this.reservationTimeRepository.findAll());
    }

    public void deleteReservationTime(final long reservationTimeId) {
        if (reservationRepository.isExistByReservationTimeId(reservationTimeId)) {
            throw new ReservationExistInReservationTimeException(reservationTimeId);
        }
        if (!reservationTimeRepository.isExistById(reservationTimeId)) {
            throw new NotExistReservationTimeException(reservationTimeId);
        }
        reservationTimeRepository.deleteById(reservationTimeId);
    }

    public ReservationTimeCreateResponse createReservationTime(final String startAt) {
        final long reservationId = reservationTimeRepository.create(ReservationTime.from(startAt));
        return new ReservationTimeCreateResponse(reservationId, startAt);
    }
}
