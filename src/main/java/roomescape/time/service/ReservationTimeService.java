package roomescape.time.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.time.domain.ReservationTime;
import roomescape.time.repository.ReservationTimeRepository;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTime createTime(LocalTime startAt) {
        return reservationTimeRepository.save(startAt);
    }

    public List<ReservationTime> getTimes() {
        return reservationTimeRepository.findAll();
    }
}
