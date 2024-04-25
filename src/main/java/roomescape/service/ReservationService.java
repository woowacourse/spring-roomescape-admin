package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationDao;
import roomescape.repository.ReservationTimeDao;

@Service
@Transactional
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao,
        ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> findAll() {
        return reservationDao.findAll()
            .stream()
            .map(ReservationResponse::from)
            .toList();
    }

    public ReservationResponse create(ReservationRequest request) {
        ReservationTime reservationTime = reservationTimeDao.findById(request.timeId());
        Reservation reservation = new Reservation(request.name(), request.date(), reservationTime);
        Reservation savedReservation = reservationDao.save(reservation);
        return ReservationResponse.from(savedReservation);
    }

    public void delete(Long id) {
        boolean success = reservationDao.deleteById(id);
        if (success) {
            return;
        }
        throw new IllegalArgumentException("존재하지 않는 예약 id입니다.");
    }
}
