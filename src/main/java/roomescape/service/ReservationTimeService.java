package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.repository.H2ReservationTimeDao;

import java.util.List;

@Service
public class ReservationTimeService {

    private final H2ReservationTimeDao reservationTimeDao;

    @Autowired
    public ReservationTimeService(H2ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTime> findAll() {
        return reservationTimeDao.findAll();
    }

    public ReservationTime findById(Long id) {
        return reservationTimeDao.findById(id);
    }

    public ReservationTime save(ReservationTimeRequest reservationTimeRequest) {
        return reservationTimeDao.save(reservationTimeRequest.toReservationTime());
    }

    public void deleteById(Long id) {
        reservationTimeDao.deleteById(id);
    }
}
