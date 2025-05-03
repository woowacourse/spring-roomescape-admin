package roomescape.service;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationRepository;
import roomescape.dao.ReservationTimeRepository;
import roomescape.dto.ReservationReqDto;
import roomescape.dto.ReservationResDto;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

@Service
public class ReservationService {

    private final ReservationRepository reservationDAO;
    private final ReservationTimeRepository reservationTimeDAO;

    public ReservationService(ReservationRepository reservationDAO, ReservationTimeRepository reservationTimeDAO) {
        this.reservationDAO = reservationDAO;
        this.reservationTimeDAO = reservationTimeDAO;
    }

    public List<ReservationResDto> findAll() {
        List<Reservation> reservations = reservationDAO.findAll();
        return reservations.stream()
                .map(ReservationResDto::of)
                .toList();
    }

    public ReservationResDto addAndGet(ReservationReqDto dto) {
        ReservationTime reservationTime = reservationTimeDAO.findById(dto.timeId());
        long newReservationId = reservationDAO.addAndGet(dto.name(), dto.date(), dto.timeId());
        return ReservationResDto.of(new Reservation(newReservationId, dto.name(), dto.date(), reservationTime));
    }

    public void deleteById(Long id) {
        int rows = reservationDAO.deleteById(id);
        if (rows == 0) {
            throw new EmptyResultDataAccessException(rows);
        }
    }
}
