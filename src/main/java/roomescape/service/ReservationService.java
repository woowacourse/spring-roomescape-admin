package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.reservation.ReservationQueryingDAO;
import roomescape.reservation.Reservation;
import roomescape.reservation.ReservationRequest;
import roomescape.reservation.ReservationUpdatingDAO;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationQueryingDAO reservationQueryingDAO;
    private final ReservationUpdatingDAO reservationUpdatingDAO;

    public ReservationService(ReservationQueryingDAO reservationQueryingDAO, ReservationUpdatingDAO reservationUpdatingDAO) {
        this.reservationQueryingDAO = reservationQueryingDAO;
        this.reservationUpdatingDAO = reservationUpdatingDAO;
    }

    public List<Reservation> read() {
        return reservationQueryingDAO.findAllReservations();
    }

    public Reservation create(ReservationRequest reservationReq) {
        Long generatedId = reservationUpdatingDAO.insertWithKeyHolder(reservationReq);
        return reservationQueryingDAO.findReservationById(generatedId);
    }

    public void update(Reservation newReservation, Long id) {
        reservationUpdatingDAO.save(id, newReservation);
    }

    public void delete(Long id) {
        int count = reservationUpdatingDAO.delete(id);

        if (count == 0) {
            throw new RuntimeException("삭제하려는 예약을 찾을 수 없습니다.");
        }
    }
}
