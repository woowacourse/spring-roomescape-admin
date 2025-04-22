package roomescape.reservation.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.reservation.dao.ReservationDao;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.utils.ReservationMapper;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationMapper reservationMapper;

    @Autowired
    public ReservationService(ReservationDao reservationDao, ReservationMapper reservationMapper) {
        this.reservationDao = reservationDao;
        this.reservationMapper = reservationMapper;
    }

    public ReservationResponse addReservation(ReservationRequest reservationRequest) {
        Reservation reservation = reservationMapper.toReservation(reservationRequest);
        return reservationMapper.toReservationResponse(reservationDao.insert(reservation));
    }

    public ReservationResponse findReservationById(long id) {
        return reservationMapper.toReservationResponse(reservationDao.findById(id));
    }

    public List<ReservationResponse> findAllReservations() {
        List<Reservation> reservations = reservationDao.findAll();
        return reservations.stream()
                .map(reservationMapper::toReservationResponse)
                .toList();
    }

    public void deleteReservationById(long id) {
        reservationDao.delete(id);
    }
}
