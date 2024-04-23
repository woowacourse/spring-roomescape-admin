package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTimeResponse createTime(ReservationTimeRequest reservationTimeRequest) {
        ReservationTimeDao reservationTimeDao = new ReservationTimeDao(reservationTimeRequest.getStartAt());
        Long id = reservationTimeRepository.createTime(reservationTimeDao);
        return new ReservationTimeResponse(id, reservationTimeDao.getStartAt());
    }
}
