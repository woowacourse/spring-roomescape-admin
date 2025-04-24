package roomescape.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.dto.AddReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.exception.InvalidReservationException;
import roomescape.repository.ReservationTimeDao;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(final ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTimeResponse> findAll() {
        final List<ReservationTime> reservationTimes = reservationTimeDao.findAll();
        return reservationTimes.stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public ReservationTimeResponse save(final AddReservationTimeRequest request) {
        final ReservationTime created = ReservationTime.create(request.startAt());
        final ReservationTime saved = reservationTimeDao.save(created);
        return ReservationTimeResponse.from(saved);
    }

    public void remove(final Long id) {
        if (findById(id).isEmpty()) {
            throw new InvalidReservationException(String.format("예약 시간 번호 %d에 해당하는 예약 시간이 존재하지 않습니다.", id));
        }
        reservationTimeDao.remove(id);
    }

    private Optional<ReservationTime> findById(final Long id) {
        return reservationTimeDao.findById(id);
    }
}
