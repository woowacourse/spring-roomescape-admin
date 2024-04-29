package roomescape.general.service;

import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import roomescape.general.db.ReservationDao;
import roomescape.general.db.ReservationTimeDao;
import roomescape.general.domain.Reservation;
import roomescape.general.domain.ReservationTime;
import roomescape.general.dto.ReservationRequest;


@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(
            final ReservationDao reservationDao,
            final ReservationTimeDao reservationTimeDao
    ) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public Reservation create(final ReservationRequest reservationRequest) {
        Objects.requireNonNull(reservationRequest);
        ReservationTime reservationTime = reservationTimeDao.findById(reservationRequest.timeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 time id입니다."));
        final Reservation reservation = Reservation.from(
                reservationRequest.name(),
                reservationRequest.date(),
                reservationTime
        );
        return reservationDao.save(reservation);
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    public void deleteById(final Long id) {
        Objects.requireNonNull(id);
        reservationDao.deleteById(id);
    }
}
