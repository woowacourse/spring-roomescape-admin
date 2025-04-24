package roomescape.service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimesDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

@Service
public class ReservationTimeService {

    private final ReservationTimesDao reservationTimesDao;

    public ReservationTimeService(ReservationTimesDao reservationTimesDao) {
        this.reservationTimesDao = reservationTimesDao;
    }

    public ReservationTimeResponse create(ReservationTimeRequest request) {
        ReservationTime reservationTime = new ReservationTime(
            LocalTime.parse(request.startAt(), DateTimeFormatter.ofPattern("HH:mm"))
        );
        reservationTime = reservationTimesDao.create(reservationTime);
        return ReservationTimeResponse.from(reservationTime);
    }

    public List<ReservationTimeResponse> findAll() {
        return reservationTimesDao.findAll()
            .stream()
            .map(ReservationTimeResponse::from)
            .toList();
    }

    public void deleteReservationTime(Long id) {
        reservationTimesDao.deleteById(id);
    }
}
