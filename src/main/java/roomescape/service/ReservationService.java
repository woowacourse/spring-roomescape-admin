package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dto.ReservationResponse;
import roomescape.entity.ReservationEntity;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(final ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<ReservationResponse> findAll() {
        final List<ReservationEntity> responses = reservationDao.findAll();

        return responses.stream()
                .map(ReservationEntity::toDomain)
                .map(ReservationResponse::new)
                .toList();
    }
}
