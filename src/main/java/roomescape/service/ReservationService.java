package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.controller.request.ReservationRequest;
import roomescape.controller.response.ReservationResponse;
import roomescape.controller.response.ReservationTimeResponse;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationTimeService reservationTimeService;
    private final ReservationRepository repository;

    public ReservationService(final ReservationTimeService reservationTimeService,
                              final ReservationRepository repository) {
        this.reservationTimeService = reservationTimeService;
        this.repository = repository;
    }

    public List<ReservationResponse> get() {
        return repository.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse create(final ReservationRequest request) {
        final ReservationTimeResponse reservationTimeResponse = reservationTimeService.getById(request.timeId());
        final Reservation reservation = request.toEntity(reservationTimeResponse);
        final long id = repository.add(reservation);
        return ReservationResponse.from(repository.findById(id)
                .orElseThrow(() -> new IllegalStateException("예약을 찾는 과정에서 문제가 생겼습니다.")));
    }

    public void deleteById(final Long id) {
        repository.deleteById(id);
    }
}
