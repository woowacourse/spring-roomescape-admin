package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDAO;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationDAO reservationDAO;

    public ReservationService(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    public List<Reservation> findAllReservations(){
        return reservationDAO.findAllReservations();
    }

    public Reservation create(ReservationRequest reservationRequest){
        Long id = reservationDAO.insertWithKeyHolder(reservationRequest);
        return reservationDAO.findReservationById(id);
    }

    public void delete(Long id){
        reservationDAO.delete(id);
    }
}
