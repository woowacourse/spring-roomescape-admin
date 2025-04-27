package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.AddReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.InvalidReservationException;
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

    public List<ReservationResponse> findAll() {
        final List<Reservation> reservations = reservationDao.findAll();
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse save(final AddReservationRequest request) {
        final ReservationTime createdTime = ReservationTime.create(request.time());
        final ReservationTime savedTime = reservationTimeDao.save(createdTime);

        final Reservation created = Reservation.create(request.name(), request.date(), savedTime);
        final Reservation saved = reservationDao.save(created);
        return ReservationResponse.from(saved);
    }

    public void remove(final Long id) {
        findReservationById(id);
        reservationDao.remove(id);
    }

    private void findReservationById(final Long id) {
        reservationDao.findById(id)
                .orElseThrow(() -> new InvalidReservationException("존재하지 않는 예약 시간입니다."));
    }
}
