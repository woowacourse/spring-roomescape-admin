package roomescape.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.AddReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.InvalidReservationException;
import roomescape.repository.ReservationDao;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(final ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<ReservationResponse> findAll() {
        final List<Reservation> reservations = reservationDao.findAll();
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse save(final AddReservationRequest request) {
        final Reservation created = Reservation.create(request.name(), request.date(), request.time());
        final Reservation saved = reservationDao.save(created);
        return ReservationResponse.from(saved);
    }

    public void remove(final Long id) {
        if (findById(id).isEmpty()) {
            throw new InvalidReservationException(String.format("예약 번호 %d에 해당하는 예약이 존재하지 않습니다.", id));
        }
        reservationDao.remove(id);
    }

    private Optional<Reservation> findById(final Long id) {
        return reservationDao.findById(id);
    }
}
