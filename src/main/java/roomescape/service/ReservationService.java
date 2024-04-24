package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationDto;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    public Reservation save(ReservationDto reservationDto) {
        ReservationTime reservationTime = reservationTimeDao.findById(reservationDto.timeId());
        Long id = reservationDao.save(reservationDto);
        return new Reservation.Builder()
                .id(id)
                .name(reservationDto.name())
                .date(reservationDto.date())
                .time(reservationTime)
                .build();
    }

    public void delete(long id) {
        reservationDao.delete(id);
    }
}
