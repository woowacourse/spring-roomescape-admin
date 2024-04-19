package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationDao;
import roomescape.repository.ReservationTimeDao;
import roomescape.service.dto.ReservationServiceRequest;
import roomescape.service.dto.ReservationServiceResponse;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationServiceResponse create(ReservationServiceRequest reservationServiceRequest) {
        ReservationTime reservationTime = reservationTimeDao.findById(reservationServiceRequest.timeId());
        Reservation reservation = reservationServiceRequest.toReservation(reservationTime);

        reservationDao.save(reservation);

        return ReservationServiceResponse.from(reservation);
    }

    public List<ReservationServiceResponse> findAll() {
        List<Reservation> reservations = reservationDao.findAll();

        return reservations.stream()
                .map(ReservationServiceResponse::from)
                .toList();
    }

    public void delete(Long id) {
        reservationDao.deleteById(id);
    }
}
