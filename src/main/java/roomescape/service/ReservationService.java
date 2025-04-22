package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;

@Service
public class ReservationService {

    @Autowired
    ReservationDao reservationDao;

    public List<ReservationResponse> findAll() {
        return reservationDao.findAll();
    }

    public int create(ReservationCreateRequest request) {
        return reservationDao.create(request);
    }

    public int delete(Long id) {
        return reservationDao.delete(id);
    }
}
