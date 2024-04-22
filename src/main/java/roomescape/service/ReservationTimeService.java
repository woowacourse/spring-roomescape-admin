package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import roomescape.db.ReservationTimeRepository;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;


@Service
public class ReservationTimeService {
    @Autowired
    ReservationTimeRepository reservationTimeRepository;

    public Long createTimes(ReservationTimeRequest reservationTimeRequest) {
        return reservationTimeRepository.createReservationTime(reservationTimeRequest);
    }

    public List<ReservationTime> getTimes() {
        return reservationTimeRepository.getReservationTimes();
    }

    public void deleteTimes(@PathVariable long id) {
        reservationTimeRepository.deleteById(id);
    }

    public ReservationTime findById(final Long id) {
        return reservationTimeRepository.findById(id);
    }

    public void deleteById(final long id) {
        reservationTimeRepository.deleteById(id);
    }
}
