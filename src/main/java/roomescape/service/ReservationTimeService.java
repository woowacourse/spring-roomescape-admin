package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.ReservationRepository;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimeRepository;
import roomescape.service.request.CreateReservationTimeRequest;

@Service
@Transactional(readOnly = true)
public class ReservationTimeService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository timeRepository;

    public ReservationTimeService(
            ReservationRepository reservationRepository,
            ReservationTimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    @Transactional
    public ReservationTime addTime(CreateReservationTimeRequest request) {
        ReservationTime reservationTime = new ReservationTime(request.startAt());

        return timeRepository.save(reservationTime);
    }

    @Transactional
    public void deleteBy(long id) {
        int timeUsedCount = reservationRepository.countBy(id);
        if (timeUsedCount > 0) {
            throw new IllegalStateException("다른 예약에서 해당 예약 시간을 사용하고 있습니다.");
        }

        timeRepository.deleteBy(id);
    }

    public List<ReservationTime> findTimes() {
        return timeRepository.findAll();
    }
}
