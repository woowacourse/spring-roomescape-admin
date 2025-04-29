package roomescape.service;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDAO;
import roomescape.dto.ReservationReqDto;
import roomescape.dto.ReservationResDto;
import roomescape.model.Reservation;

@Service
public class ReservationService {

    private final ReservationDAO reservationDAO;

    public ReservationService(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    public List<ReservationResDto> findAll() {
        List<Reservation> reservations = reservationDAO.findAll();
        return reservations.stream()
                .map(ReservationResDto::of)
                .toList();
    }

    public ReservationResDto addAndGet(ReservationReqDto dto) {
        Reservation newReservation = reservationDAO.addAndGet(dto.name(), dto.date(), dto.timeId());
        return ReservationResDto.of(newReservation);
    }

    public void deleteById(Long id) {
        int rows = reservationDAO.deleteById(id);
        if (rows == 0) {
            throw new EmptyResultDataAccessException(rows);
        }
    }
}
