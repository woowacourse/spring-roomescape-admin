package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.db.ReservationTimeRepository;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;


@Service
public class ReservationTimeService {
    @Autowired
    ReservationTimeRepository reservationTimeRepository;

    public Long create(ReservationTimeRequest reservationTimeRequest) {
        return reservationTimeRepository.create(reservationTimeRequest);
    }

    public List<ReservationTime> findAll() {
        return reservationTimeRepository.findAll();
    }

    public ReservationTime findById(final Long id) {
        return reservationTimeRepository.findById(id);
    }

    public void deleteById(final long id) {
        reservationTimeRepository.deleteById(id);
    }
}
