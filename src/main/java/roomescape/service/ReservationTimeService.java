package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.repository.TimeQueryingRepository;
import roomescape.repository.TimeUpdatingRepository;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;

import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationTimeService {
    private final TimeQueryingRepository timeQueryingRepository;
    private final TimeUpdatingRepository timeUpdatingRepository;

    public ReservationTimeService(TimeQueryingRepository timeQueryingRepository, TimeUpdatingRepository timeUpdatingRepository) {
        this.timeQueryingRepository = timeQueryingRepository;
        this.timeUpdatingRepository = timeUpdatingRepository;
    }

    public List<ReservationTime> findAll() {
        return timeQueryingRepository.findAll();
    }

    public ReservationTime findById(Long id) {
        return timeQueryingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 시간입니다."));
    }

    public ReservationTime save(ReservationTimeRequest request) {
        LocalTime startAt = LocalTime.parse(request.getStartAt());
        if (timeQueryingRepository.existsByStartAt(startAt)) {
            throw new IllegalArgumentException("이미 존재하는 예약 시간입니다.");
        }
        ReservationTime reservationTime = new ReservationTime(null, startAt);
        Long id = timeUpdatingRepository.insert(reservationTime);
        return new ReservationTime(id, startAt);
    }

    public void delete(Long id) {
        timeUpdatingRepository.delete(id);
    }
}
