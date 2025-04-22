package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.controller.request.ReservationTimeRequest;
import roomescape.controller.response.ReservationTimeResponse;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository repository;

    public ReservationTimeService(final ReservationTimeRepository repository) {
        this.repository = repository;
    }

    public List<ReservationTimeResponse> get() {
        return repository.findAll();
    }

    public ReservationTimeResponse create(final ReservationTimeRequest request) {
        final long id = repository.add(request);
        return repository.findById(id)
                .orElseThrow(() -> new IllegalStateException("예약 시간을 찾는 과정에서 문제가 생겼습니다."));
    }

    public void deleteById(final Long id) {
        repository.deleteById(id);
    }
}
