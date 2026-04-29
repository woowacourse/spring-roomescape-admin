package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationRepository;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;

@Service
public class ReservationService {

    private final ReservationTimeDao reservationTimeDao;
    private final ReservationRepository reservationRepository;
    public ReservationService(ReservationTimeDao reservationTimeDao, ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<Reservation> selectReservations() {
        return reservationRepository.selectReservations();
    }

    public Reservation createReservation(ReservationRequest reservationRequest) {
        ReservationTime reservationTime = reservationTimeDao.selectById(reservationRequest.getTimeId());
        return reservationRepository.insertReservation(reservationRequest, reservationTime);
    }

    public void deleteReservation(long id) {
        reservationRepository.deleteReservation(id);
    }
}
