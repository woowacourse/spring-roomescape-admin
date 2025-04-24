package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.TimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;

@Service
public class ReservationService {

    @Autowired
    ReservationDao reservationDao;

    @Autowired
    private TimeDao timeDao;

    public List<ReservationResponse> findAll() {
        List<Reservation> reservationDaoAll = reservationDao.findAll();

        return reservationDaoAll.stream()
                .map(reservation -> {
                    return ReservationResponse.toDto(reservation);
                })
                .toList();
    }

    public Long create(ReservationCreateRequest request) {
        ReservationTime reservationTime = timeDao.findById(request.timeId());
        Reservation reservation = request.toReservation(reservationTime);
        return reservationDao.create(reservation);
    }

    public ReservationTime findReservationTimeById(final Long id) {
        return timeDao.findById(id);
    }

    public int delete(Long id) {
        return reservationDao.delete(id);
    }
}
