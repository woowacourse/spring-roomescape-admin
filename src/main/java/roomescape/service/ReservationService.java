package roomescape.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationDao;
import roomescape.repository.ReservationTimeDao;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(final ReservationDao reservationDao, final ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationResponse save(final ReservationRequest request) {
        final Optional<ReservationTime> findTime = reservationTimeDao.findById(request.timeId());
        if (findTime.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 시간입니다.");
        }
        final Reservation created = Reservation.create(request.name(), request.date(), findTime.get());
        final Reservation saved = reservationDao.save(created, findTime.get().getId());
        return ReservationResponse.from(saved);
    }

    public List<ReservationResponse> findAll() {
        final List<Reservation> reservations = reservationDao.findAll();
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public Optional<Reservation> findById(final long id) {
        return reservationDao.findById(id);
    }

    public void remove(final long id) {
        final Reservation findReservation = findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("id: %s는 존재하지 않는 id 입니다.", id)));
        reservationDao.remove(findReservation.getId());
    }
}
