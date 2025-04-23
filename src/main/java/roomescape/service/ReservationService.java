package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.controller.request.ReservationRequest;
import roomescape.controller.response.ReservationResponse;
import roomescape.controller.response.ReservationTimeResponse;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;
import roomescape.service.exception.ReservationNotFoundException;

@Service
public class ReservationService {

    private final ReservationTimeService reservationTimeService;
    private final ReservationRepository repository;

    public ReservationService(final ReservationTimeService reservationTimeService,
                              final ReservationRepository repository) {
        this.reservationTimeService = reservationTimeService;
        this.repository = repository;
    }

    public List<ReservationResponse> getAll() {
        return repository.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    private ReservationResponse getById(Long id) {
        return ReservationResponse.from(repository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("예약을 찾을 수 없습니다.")));
    }

    public ReservationResponse create(final ReservationRequest request) {
        final ReservationTimeResponse reservationTimeResponse = reservationTimeService.getById(request.timeId());
        final Reservation reservation = request.toEntity(reservationTimeResponse);
        final long id = repository.add(reservation);
        return getById(id);
    }

    public void deleteById(final Long id) {
        final ReservationResponse reservationResponse = getById(id);
        repository.deleteById(reservationResponse.id());
    }
}
