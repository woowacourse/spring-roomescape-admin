package roomescape.service;

import java.util.List;

import org.springframework.stereotype.Service;

import roomescape.dto.ReservationCreateRequest;
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

    public ReservationResponse createReservation(final ReservationCreateRequest request) {
        ReservationTime reservationTime = reservationTimeDao.findById(request.timeId());

        Reservation reservation = reservationDao.insert(
                new Reservation(null, request.name(), request.date(), reservationTime)
        );
        return new ReservationResponse(reservation);
    }

    public void deleteReservation(final Long id) {
        reservationDao.deleteById(id);
    }

    public List<ReservationResponse> findAllReservations() {
        return reservationDao.findAll()
                .stream()
                .map(ReservationResponse::new)
                .toList();
    }
}
