package roomescape.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.model.Reservation;
import roomescape.repository.ReservationDao;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(final ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public ReservationResponse save(final ReservationRequest request) {
        final Reservation created = Reservation.create(request.name(), request.date(), request.time());
        final Reservation saved = reservationDao.save(created);
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
        final Optional<Reservation> findReservation = findById(id);
        if (findReservation.isEmpty()) {
            throw new IllegalArgumentException(String.format("id: %s는 존재하지 않는 id 입니다.", id));
        }
        reservationDao.remove(id);
    }
}
