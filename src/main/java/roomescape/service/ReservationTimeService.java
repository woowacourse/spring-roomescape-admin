package roomescape.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.dto.ReservationTimeRequest;
import roomescape.service.dto.ReservationTimeResponse;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    @Transactional
    public ReservationTimeResponse create(ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = reservationTimeRequest.toTime();
        ReservationTime saved = reservationTimeRepository.save(reservationTime);

        return new ReservationTimeResponse(saved);
    }

    public ReservationTimeResponse findOne(long id) {
        ReservationTime found = reservationTimeRepository.findById(id).orElseThrow();

        return new ReservationTimeResponse(found);
    }

    public List<ReservationTimeResponse> findAll() {
        return reservationTimeRepository.findAll().stream()
                .map(ReservationTimeResponse::new)
                .toList();
    }

    @Transactional
    public void remove(Long id) {
        reservationTimeRepository.delete(id);
    }
}
