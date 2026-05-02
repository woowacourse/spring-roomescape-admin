package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationSaveDto;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<Reservation> readAll() {
        return reservationDao.selectAll();
    }

    public Reservation reserve(ReservationSaveDto dto) {
        ReservationTime reservationTime = readReservationTime(dto.timeId());
        return reservationDao.insert(new Reservation(dto.name(), dto.date(), reservationTime));
    }

    public void cancel(Long id) {
        boolean isDeleted = reservationDao.delete(id);
        if (!isDeleted) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 id 이므로, Reservation을 삭제할 수 없습니다.");
        }
    }

    private ReservationTime readReservationTime(Long id) {
        return reservationTimeDao.select(id)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 ReservationTime 입니다."));
    }

}
