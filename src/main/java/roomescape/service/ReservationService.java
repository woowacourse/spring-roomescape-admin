package roomescape.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationRequestV2;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationResponseV2;
import roomescape.model.Reservation;
import roomescape.model.Reservation2;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationDao;
import roomescape.repository.TimeDao;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final TimeDao timeDao;

    public ReservationService(final ReservationDao reservationDao, final TimeDao timeDao) {
        this.reservationDao = reservationDao;
        this.timeDao = timeDao;
    }

    public ReservationResponse save(final ReservationRequest request) {
        final Reservation created = Reservation.create(request.name(), request.date(), request.time());
        final Reservation saved = reservationDao.save(created);
        return ReservationResponse.from(saved);
    }

    public ReservationResponseV2 saveV2(final ReservationRequestV2 requestV2) {
        final Optional<ReservationTime> findTime = timeDao.findById(requestV2.timeId());
        if (findTime.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 시간입니다.");
        }
        final Reservation2 created = Reservation2.create(requestV2.name(), requestV2.date(), findTime.get());
        final Reservation2 saved = reservationDao.save2(created, findTime.get().getId()); //TODO 리팩터링
        return ReservationResponseV2.from(saved);
    }

    public List<ReservationResponse> findAll() {
        final List<Reservation> reservations = reservationDao.findAll();
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public List<ReservationResponseV2> findAll2() {
        final List<Reservation2> reservations = reservationDao.findAll2();
        return reservations.stream()
                .map(ReservationResponseV2::from)
                .toList();
    }

    public Optional<Reservation2> findById(final long id) {
        return reservationDao.findById(id);
    }

    public void remove(final long id) {
        final Optional<Reservation2> findReservation = findById(id);
        if (findReservation.isEmpty()) {
            throw new IllegalArgumentException(String.format("timeId: %s는 존재하지 않는 timeId 입니다.", id));
        }
        reservationDao.remove(id);
    }
}
