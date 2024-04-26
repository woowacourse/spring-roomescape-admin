package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeRepository;
import roomescape.domain.ReservationTime;
import roomescape.exception.InvalidReservationException;
import roomescape.service.dto.ReservationTimeRequest;
import roomescape.service.dto.ReservationTimeResponse;

import java.util.List;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(final ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTimeResponse create(final ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = reservationTimeRepository.save(new ReservationTime(reservationTimeRequest.startAt()));
        return new ReservationTimeResponse(reservationTime);
    }

    public List<ReservationTimeResponse> findAll() {
        return reservationTimeRepository.findAll().stream()
                .map(ReservationTimeResponse::new)
                .toList();
    }

    public void deleteById(final long id) {
        reservationTimeRepository.deleteById(id);
    }

    public ReservationTime findById(final long id) {
        return reservationTimeRepository.findById(id)
                .orElseThrow(() -> new InvalidReservationException("존재하지 않는 예약 시간입니다. id: " + id));
    }
}
