package roomescape.service;

import java.time.Clock;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDate;
import roomescape.domain.ReservationDateTime;
import roomescape.domain.ReservationName;
import roomescape.domain.ReservationTime;
import roomescape.dto.CreateReservationRequest;
import roomescape.dto.ReservationResponse;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;
    private final Clock clock;

    public ReservationService(
            final ReservationDao reservationDao,
            final ReservationTimeDao reservationTimeDao,
            final Clock clock
    ) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
        this.clock = clock;
    }

    public void deleteReservationById(final Long id) {
        reservationDao.deleteById(id);
    }

    public ReservationResponse createReservation(final CreateReservationRequest createReservationRequest) {
        ReservationTime reservationTime = reservationTimeDao.findById(createReservationRequest.timeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약시간입니다."));
        Reservation savedReservation = reservationDao.save(
                new ReservationName(createReservationRequest.name()),
                new ReservationDateTime(
                        new ReservationDate(createReservationRequest.date()),
                        reservationTime,
                        clock
                )
        );
        return ReservationResponse.from(savedReservation);
    }

    public List<ReservationResponse> getAllReservations() {
        return ReservationResponse.from(reservationDao.findAll());
    }
}
