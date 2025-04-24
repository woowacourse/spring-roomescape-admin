package roomescape.business.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.data.dao.ReservationDao;
import roomescape.business.domain.Reservation;
import roomescape.presentation.dto.ReservationRequest;
import roomescape.presentation.dto.ReservationResponse;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(final ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<ReservationResponse> findAll() {
        return reservationDao.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse create(final ReservationRequest reservationRequest) {
        final Reservation reservation = reservationRequest.toDomain();
        final Long id = reservationDao.save(reservation);
        return ReservationResponse.withId(reservation, id);
    }

    public void remove(final Long id) {
        final int rowNum = reservationDao.remove(id);

        if (rowNum == 0) {
            throw new IllegalArgumentException("해당하는 id가 없습니다.");
        }
    }
}
