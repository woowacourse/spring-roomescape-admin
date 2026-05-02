package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTime> findAll(){
        return reservationTimeDao.findAll();
    }

    public ReservationTime save(ReservationTime reservationTime){
        if(reservationTimeDao.existsByStartAt(reservationTime.getStartAt())){
            throw new IllegalArgumentException("이미 존재하는 예약시간입니다.");
        }
        return reservationTimeDao.save(reservationTime);
    }

    public void deleteById(Long id){
        reservationTimeDao.deleteById(id);
    }
}
