package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dto.ReservationResponseDto;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<ReservationResponseDto> getAllReservations() {
        List<Reservation> reservations = reservationDao.getAll();
        return reservations.stream()
                .map(ReservationResponseDto::new)
                .toList();
    }

    public ReservationResponseDto addReservation(ReservationRequestDto reservationRequestDto) {
        long id = reservationDao.add(reservationRequestDto);
        return new ReservationResponseDto(id, reservationRequestDto);
    }

    public void deleteReservation(long id) {
        reservationDao.delete(id);
    }
}
