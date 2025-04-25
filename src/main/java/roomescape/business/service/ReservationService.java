package roomescape.business.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.business.domain.Reservation;
import roomescape.business.domain.Time;
import roomescape.data.dao.ReservationDao;
import roomescape.presentation.dto.ReservationRequest;
import roomescape.presentation.dto.ReservationResponse;

@Service
public class ReservationService {

    private final TimeService timeService;
    private final ReservationDao reservationDao;

    @Autowired
    public ReservationService(final TimeService timeService, final ReservationDao reservationDao) {
        this.timeService = timeService;
        this.reservationDao = reservationDao;
    }

    public ReservationResponse create(final ReservationRequest reservationRequest) {
        final Time time = timeService.find(reservationRequest.timeId());
        final Reservation reservation = reservationRequest.toDomain(time);
        final Long id = reservationDao.save(reservation);

        return ReservationResponse.withId(reservation, id);
    }

    public List<ReservationResponse> findAll() {
        return reservationDao.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public void remove(final Long id) {
        final int rowNum = reservationDao.remove(id);

        validateIdExists(rowNum);
    }

    private static void validateIdExists(final int rowNum) {
        if (rowNum == 0) {
            throw new IllegalArgumentException("해당하는 id가 없습니다.");
        }
    }
}
