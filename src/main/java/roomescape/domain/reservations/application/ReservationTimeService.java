package roomescape.domain.reservations.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.reservations.entity.ReservationTime;
import roomescape.domain.reservations.infrastructure.ReservationTimeRepository;
import roomescape.domain.reservations.presentation.dto.ReservationTimeRequest;
import roomescape.domain.reservations.presentation.dto.ReservationTimeResponse;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @Transactional
    public ReservationTimeResponse saveTime(ReservationTimeRequest request) {
        validateSaveRequest(request);
        ReservationTime reservationTime = ReservationTime.of(
                null,
                request.startAt()
        );
        ReservationTime savedReservationTime = reservationTimeRepository.save(reservationTime);
        return ReservationTimeResponse.from(savedReservationTime);
    }

    public List<ReservationTime> getTimes() {
        return reservationTimeRepository.findAll();
    }

    public void deleteTime(Long id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        reservationTimeRepository.deleteById(id);
    }

    private void validateSaveRequest(ReservationTimeRequest request) {
        if (request == null) {
            throw new IllegalArgumentException();
        }
        if (request.startAt() == null) {
            throw new IllegalArgumentException();
        }
    }
}
