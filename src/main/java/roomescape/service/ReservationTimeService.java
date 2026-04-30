package roomescape.service;

import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@Service
@RequiredArgsConstructor
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public long createTime(LocalTime startAt) {
        final ReservationTime reservationTime = new ReservationTime(startAt);
        return reservationTimeRepository.save(reservationTime);
    }

    public List<ReservationTime> getAllTimes() {
        return reservationTimeRepository.findAll();
    }

    public void deleteTime(long id) {
        reservationTimeRepository.deleteById(id);
    }
}
