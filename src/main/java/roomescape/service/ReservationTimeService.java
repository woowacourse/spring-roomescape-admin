package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.controller.dto.request.ReservationTimeRequest;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository repository;

    public ReservationTimeService(ReservationTimeRepository repository) {
        this.repository = repository;
    }

    public ReservationTime addReservationTime(ReservationTimeRequest request) {
        return repository.save(new ReservationTime(request.startAt()));
    }

    public List<ReservationTime> getReservationTimes() {
        return repository.findAll();
    }

    public void deleteReservation(Long id) {
        repository.delete(id);
    }
}
