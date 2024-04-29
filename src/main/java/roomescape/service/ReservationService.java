package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.CreateReservationRequest;
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

    public ReservationResponse save(final CreateReservationRequest request) {
        final ReservationTime findTime = reservationTimeDao.findById(request.timeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 시간입니다."));

        final Reservation reservation = new Reservation(request.name(), request.date(), findTime);
        final Reservation saved = reservationDao.save(reservation, findTime.getId());
        return ReservationResponse.from(saved);
    }

    public List<ReservationResponse> findAll() {
        final List<Reservation> reservations = reservationDao.findAll();
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public void remove(final long id) {
        final Reservation findReservation = reservationDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("id: %s는 존재하지 않는 id 입니다.", id)));
        reservationDao.remove(findReservation.getId());
    }
}
