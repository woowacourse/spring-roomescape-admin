package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimeRepository;
import roomescape.service.request.CreateReservationTimeRequest;

@Service
@Transactional(readOnly = true)
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @Transactional
    public ReservationTime addTime(CreateReservationTimeRequest request) {
        ReservationTime reservationTime = new ReservationTime(request.startAt());

        return reservationTimeRepository.saveOne(reservationTime);
    }

    @Transactional
    public void deleteBy(long id) {
        reservationTimeRepository.deleteBy(id);
    }

    public List<ReservationTime> findTimes() {
        return reservationTimeRepository.findAll();
    }
}
