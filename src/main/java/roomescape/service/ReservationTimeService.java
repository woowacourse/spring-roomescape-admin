package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.model.ReservationTime;
import roomescape.repository.JdbcReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final JdbcReservationTimeRepository jdbcReservationTimeRepository;

    public ReservationTimeService(JdbcReservationTimeRepository jdbcReservationTimeRepository) {
        this.jdbcReservationTimeRepository = jdbcReservationTimeRepository;
    }

    public ReservationTime addTime(String start_at) {
        return jdbcReservationTimeRepository.addTime(start_at);
    }

    public List<ReservationTime> getAllTime() {
        return jdbcReservationTimeRepository.getAllTime();
    }

    public Integer deleteTime(Long id) {
        return jdbcReservationTimeRepository.deleteTime(id);
    }

    public ReservationTime getReservationTimeById(Long id) {
        return jdbcReservationTimeRepository.getReservationTimeById(id);
    }
}
