package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.controller.request.ReservationTimeRequest;
import roomescape.controller.response.ReservationTimeResponse;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.exception.ReservationTimeNotFoundException;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository repository;

    public ReservationTimeService(final ReservationTimeRepository repository) {
        this.repository = repository;
    }

    public List<ReservationTimeResponse> getAll() {
        return repository.findAll().stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public ReservationTimeResponse getById(final Long id) {
        return ReservationTimeResponse.from(
                repository.findById(id).orElseThrow(() -> new ReservationTimeNotFoundException("예약 시간을 찾을 수 없습니다.")));
    }

    public ReservationTimeResponse create(final ReservationTimeRequest request) {
        final long id = repository.add(request.toEntity());
        return getById(id);
    }

    public void deleteById(final Long id) {
        final ReservationTimeResponse reservationTimeResponse = getById(id);
        repository.deleteById(reservationTimeResponse.id());
    }
}
