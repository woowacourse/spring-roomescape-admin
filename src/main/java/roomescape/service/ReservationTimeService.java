package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository timeRepository;

    public ReservationTimeService(ReservationRepository reservationRepository,
                                  ReservationTimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public List<ReservationTime> findAll() {
        return timeRepository.findAll();
    }

    public ReservationTime save(ReservationTime time) {
        return timeRepository.save(time);
    }

    public void delete(long id) {
        if (reservationRepository.isExistsByTimeId(id)) {
            throw new IllegalArgumentException("예약이 존재하여 예약시간을 삭제할 수 없습니다. id: " + id);
        }
        timeRepository.delete(id);
    }
}
