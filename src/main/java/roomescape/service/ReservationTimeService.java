package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository repository;

    public ReservationTimeService(ReservationTimeRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ReservationTimeResponse create(ReservationTimeRequest request) {
        Long id = repository.create(request.getStartAt());
        return ReservationTimeResponse.of(id, request);
    }

    @Transactional
    public List<ReservationTimeResponse> findAll() {
        List<ReservationTime> reservationTimes = repository.findAll();
        return reservationTimes.stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public int delete(Long id) {
        return repository.delete(id);
    }
}
