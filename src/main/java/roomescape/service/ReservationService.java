package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationFindResponse;
import roomescape.dto.ReservationSaveRequest;
import roomescape.repository.ReservationDao;

@Service
public class ReservationService {

    private final ReservationTimeService reservationTimeService;
    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeService reservationTimeService) {
        this.reservationDao = reservationDao;
        this.reservationTimeService = reservationTimeService;
    }

    public List<ReservationFindResponse> findAll() {
        List<Reservation> reservations = reservationDao.findAll();
        return reservations.stream()
                .map(ReservationFindResponse::from)
                .toList();
    }

    public void deleteById(Long id) {
        reservationDao.deleteById(id);
    }

    public Reservation save(ReservationSaveRequest request) {
        ReservationTime reservationTime = reservationTimeService.findById(request.timeId());
        Reservation reservation = request.toEntity(reservationTime);
        return reservationDao.save(reservation);
    }
}
