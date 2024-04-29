package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.CreateReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationTimeDao;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(final ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTimeResponse save(final CreateReservationTimeRequest createReservationTimeRequest) {
        final ReservationTime reservationTime = new ReservationTime(createReservationTimeRequest.startAt());
        final ReservationTime saved = reservationTimeDao.save(reservationTime);
        return ReservationTimeResponse.from(saved);
    }

    public List<ReservationTimeResponse> findAll() {
        return reservationTimeDao.findAll()
                .stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public void remove(final long id) {
        final ReservationTime findReservation = reservationTimeDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("timeId: %s는 존재하지 않는 timeId 입니다.", id)));
        reservationTimeDao.remove(findReservation.getId());
    }
}
