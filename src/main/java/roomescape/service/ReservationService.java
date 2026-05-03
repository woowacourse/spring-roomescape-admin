package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dao.ReservationDAO;
import roomescape.dao.ReservationTimeDAO;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequestDTO;
import roomescape.exception.ReservationNotFoundException;
import roomescape.exception.ReservationTimeNotFoundException;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationDAO reservationDAO;
    private final ReservationTimeDAO reservationTimeDAO;

    public ReservationService(ReservationDAO reservationDAO, ReservationTimeDAO reservationTimeDAO) {
        this.reservationDAO = reservationDAO;
        this.reservationTimeDAO = reservationTimeDAO;
    }

    public List<Reservation> findAllReservations() {
        return reservationDAO.findAllReservation();
    }

    public Reservation createReservation(ReservationRequestDTO requestDTO) {
        ReservationTime time = reservationTimeDAO.findReservationTimeById(requestDTO.getTimeId());
        if (time == null) {
            throw new ReservationTimeNotFoundException("[ERROR] 존재하지 않는 예약 시간 ID입니다.");
        }

        Reservation reservation = new Reservation(requestDTO.getName(), requestDTO.getDate(), time);
        Long generatedId = reservationDAO.insertWithKeyHolder(reservation);
        return new Reservation(generatedId, reservation.getName(), reservation.getDate(), time);
    }

    public void deleteReservation(Long id) {
        int deletedRowNum = reservationDAO.delete(id);

        if (deletedRowNum == 0) {
            throw new ReservationNotFoundException("[ERROR] 존재하지 않는 예약 ID입니다.");
        }

        reservationDAO.delete(id);
    }
}
