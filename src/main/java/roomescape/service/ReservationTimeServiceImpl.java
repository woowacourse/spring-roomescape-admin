package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.entity.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeServiceImpl implements ReservationTimeService {

    @Autowired
    ReservationTimeRepository reservationTimeRepository;

    @Override
    public List<ReservationTime> findAll() {
        return reservationTimeRepository.findAll();
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        return reservationTimeRepository.save(reservationTime);
    }

    @Override
    public void delete(Long id) {
        reservationTimeRepository.delete(id);
    }
}
