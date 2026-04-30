package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.repository.TimeQueryingRepository;
import roomescape.repository.TimeUpdatingRepository;
import roomescape.domain.ReservationTime;

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

    public ReservationTime save(ReservationTime reservationTime) {
        Long id = timeUpdatingRepository.insert(reservationTime);
        return ReservationTime.toEntity(reservationTime, id);
    }

    public void delete(Long id) {
        timeUpdatingRepository.delete(id);
    }
}