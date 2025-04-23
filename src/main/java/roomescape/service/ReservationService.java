package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;

@Service
public class ReservationService {

    @Autowired
    ReservationDao reservationDao;

    public List<ReservationResponse> findAll() {
        List<Reservation> reservationDaoAll = reservationDao.findAll();

        return reservationDaoAll.stream()
                .map(reservation -> {
                    return ReservationResponse.toDto(reservation);
                })
                .toList();
    }

    public Long create(ReservationCreateRequest request) {

        Reservation reservation = request.toReservation();
        return reservationDao.createWithMap(reservation);
    }

    public int delete(Long id) {
        return reservationDao.delete(id);
    }
}
