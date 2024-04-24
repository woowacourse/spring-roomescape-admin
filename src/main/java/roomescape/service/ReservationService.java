package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.dto.ReservationSaveDto;
import roomescape.entity.Reservation;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    @Autowired
    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<ReservationResponseDto> findReservations() {
        List<Reservation> reservations = reservationDao.findAll();
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
