package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.controller.dto.request.ReservationTimeRequest;
import roomescape.controller.dto.response.ReservationTimeResponse;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository repository;

    public ReservationTimeService(ReservationTimeRepository repository) {
        this.repository = repository;
    }

    public ReservationTimeResponse addReservationTime(ReservationTimeRequest request) {
        ReservationTime saved = repository.save(new ReservationTime(request.startAt()));
        return ReservationTimeResponse.from(saved);
    }

    public List<ReservationTime> getReservationTimes() {
        return repository.findAll();
    }

    public void deleteReservationTime(Long id) {
        repository.delete(id);
    }
}
