package roomescape.service.reservation;

import java.util.List;
import org.springframework.stereotype.Component;
import roomescape.domain.reservation.Reservation;
import roomescape.dto.reservation.ReservationCreateRequest;
import roomescape.dto.reservation.ReservationResponse;
import roomescape.exception.NotFoundException;
import roomescape.repository.reservation.ReservationDao;
import roomescape.service.time.ReservationTimeService;

@Component
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeService reservationTimeService;

    public ReservationService(ReservationDao reservationDao, ReservationTimeService reservationTimeService) {
        this.reservationDao = reservationDao;
        this.reservationTimeService = reservationTimeService;
    }

    public List<ReservationResponse> findAll() {
        return reservationDao.findAll().stream()
            .map(ReservationResponse::from)
            .toList();
    }

    public ReservationResponse create(ReservationCreateRequest request) {
        Reservation reservation = new Reservation(
            request.name(),
            request.date(),
            reservationTimeService.findById(request.timeId())
        );

        reservationDao.save(reservation);

        return ReservationResponse.from(reservation);
    }

    public void deleteById(long id) {
        boolean isDeleted = reservationDao.deleteById(id);
        if (!isDeleted) {
            throw new NotFoundException("존재하지 않는 예약입니다.");
        }
    }
}
