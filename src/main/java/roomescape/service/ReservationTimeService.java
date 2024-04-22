package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.ReservationTimeDto;
import roomescape.entity.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public long addTime(ReservationTimeDto reservationTimeDto) {
        return reservationTimeRepository.insert(reservationTimeDto);
    }

    public List<ReservationTime> getAllTimes() {
        return reservationTimeRepository.readAll();
    }

    public void deleteTimeWithId(long id) {
        reservationTimeRepository.delete(id);
    }
}
