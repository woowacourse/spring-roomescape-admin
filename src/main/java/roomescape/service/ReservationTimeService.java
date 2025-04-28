package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.model.ReservationTime;
import roomescape.repository.JdbcReservationTimeRepository;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository jdbcReservationTimeRepository) {
        this.reservationTimeRepository = jdbcReservationTimeRepository;
    }

    public ReservationTime addTime(String start_at) {
        return reservationTimeRepository.addTime(start_at);
    }

    public List<ReservationTime> getAllTime() {
        return reservationTimeRepository.getAllTime();
    }

    public Integer deleteTime(Long id) {
        return reservationTimeRepository.deleteTime(id);
    }

    public ReservationTime getReservationTimeById(Long id) {
        return reservationTimeRepository.getReservationTimeById(id);
    }
}
