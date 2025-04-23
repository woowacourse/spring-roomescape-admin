package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.entity.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(final ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTimeResponse createReservationTime(final ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = new ReservationTime(reservationTimeRequest.startAt());
        ReservationTime createdReservationTime = reservationTimeRepository.save(reservationTime);
        return ReservationTimeResponse.from(createdReservationTime);
    }
}
