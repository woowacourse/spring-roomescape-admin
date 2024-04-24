package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationTimeCreateRequest;
import roomescape.dto.response.ReservationTimeCreateResponse;
import roomescape.dto.response.ReservationTimesResponse;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTimesResponse getReservationTimes() {
        return new ReservationTimesResponse(this.reservationTimeRepository.findAll());
    }

    public void deleteReservationTime(final long reservationTimeId) {
        reservationTimeRepository.deleteById(reservationTimeId);
    }

    public ReservationTimeCreateResponse createReservationTime(final ReservationTimeCreateRequest reservationTimeCreateRequest) {
        final String startAt = reservationTimeCreateRequest.startAt();
        final long reservationId = reservationTimeRepository.create(ReservationTime.from(startAt));
        return new ReservationTimeCreateResponse(reservationId, startAt);
    }
}
