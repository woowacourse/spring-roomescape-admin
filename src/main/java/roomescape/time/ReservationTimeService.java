package roomescape.time;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.time.dto.ReservationTimeRequest;
import roomescape.time.dto.ReservationTimeResponse;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(
            @Autowired ReservationTimeDao reservationTimeDao
    ) {
        this.reservationTimeDao = reservationTimeDao;
    }


    public ReservationTimeResponse createTime(final ReservationTimeRequest request) {
        final ReservationTime notSavedReservationTime = new ReservationTime(null, request.startAt());
        final Long savedTimeId = reservationTimeDao.saveTime(notSavedReservationTime);
        final ReservationTime savedReservationTime = reservationTimeDao.findTimeById(savedTimeId);
        return ReservationTimeResponse.createResponse(savedReservationTime);
    }

    public List<ReservationTimeResponse> findAllTime() {
        return reservationTimeDao.findAllTime().stream()
                .map(ReservationTimeResponse::createResponse)
                .toList();
    }

    public void deleteTimeById(final Long id) {
        reservationTimeDao.deleteTimeById(id);
    }
}
