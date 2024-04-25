package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.dto.CreateReservationRequest;
import roomescape.dto.CreateReservationResponse;
import roomescape.mapper.ReservationMapper;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationMapper reservationMapper;

    public ReservationService(ReservationDao reservationDao, ReservationMapper reservationMapper) {
        this.reservationDao = reservationDao;
        this.reservationMapper = reservationMapper;
    }

    public List<Reservation> findAllReservations() {
        return reservationDao.findAll();
    }

    public CreateReservationResponse createReservation(CreateReservationRequest request) {
        Reservation requestedReservation = reservationMapper.toReservation(request);
        int createdId = reservationDao.create(requestedReservation);
        Reservation createdReservation = reservationDao.findById(createdId);
        return reservationMapper.toCreateReservationResponse(createdReservation);
    }

    public void deleteReservation(int id) {
        reservationDao.delete(id);
    }
}
