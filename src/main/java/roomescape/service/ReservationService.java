package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.dto.ReservationSaveDto;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    @Autowired
    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationResponseDto> findReservations() {
        List<Reservation> reservations = reservationDao.findAll();
        return reservations.stream()
                .map(ReservationResponseDto::from)
                .toList();
    }

    public ReservationResponseDto addReservation(ReservationRequestDto reservationDto) {
        ReservationTime reservationTime = reservationTimeDao.findById(reservationDto.getTimeId());
        long id = reservationDao.save(ReservationSaveDto.from(reservationDto, reservationTime.getStartAt()));
        Reservation reservation = new Reservation(
                id,
                reservationDto.getName(),
                reservationDto.getDate(),
                reservationTime
        );
        return ReservationResponseDto.from(reservation);
    }

    public void deleteReservation(long id) {
        reservationDao.delete(id);
    }
}
