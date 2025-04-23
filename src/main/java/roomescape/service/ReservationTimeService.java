package roomescape.service;

import roomescape.dto.ReservationTimeResponse;
import roomescape.entity.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

public class ReservationTimeService {

    private ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTimeResponse createReservationTime(ReservationTime time) {
        ReservationTime reservationTime = reservationTimeRepository.save(time);
        return ReservationTimeResponse.from(reservationTime);
    }
}
