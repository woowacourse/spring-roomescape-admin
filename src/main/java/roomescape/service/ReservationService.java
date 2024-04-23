package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.dto.ReservationSaveDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.entity.Reservation;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    @Autowired
    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<ReservationResponseDto> findReservations() {
        List<Reservation> reservations = reservationDao.findAllReservations();
        return reservations.stream()
                .map(ReservationResponseDto::from)
                .toList();
    }

    public ReservationResponseDto addReservation(ReservationRequestDto reservationDto) {
        long id = reservationDao.save(ReservationSaveDto.from(reservationDto));
        Reservation reservation = reservationDao.findById(id);
        return ReservationResponseDto.from(reservation);
    }

    public void deleteReservation(long id) {
        reservationDao.delete(id);
    }
}
