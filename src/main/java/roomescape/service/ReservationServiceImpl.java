package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Component;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.idgenerator.AutoIncrementIdGenerator;
import roomescape.idgenerator.IdGenerator;

@Component
public class ReservationServiceImpl implements ReservationService {
    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;
    private final IdGenerator idGenerator = new AutoIncrementIdGenerator();

    public ReservationServiceImpl(ReservationDao reservationDao,
                                  ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    @Override
    public List<ReservationResponse> findAll() {
        List<Reservation> reservations = reservationDao.findAll();
        return reservations.stream()
                .map(ReservationResponse::new)
                .toList();
    }

    @Override
    public ReservationResponse save(ReservationRequest reservationRequest) {
        long timeId = reservationRequest.timeId();
        ReservationTime time = reservationTimeDao.findById(timeId);
        Reservation reservation = reservationRequest.toDomain(idGenerator.generateNewId(), time);
        reservationDao.save(reservation);
        return new ReservationResponse(reservation);
    }

    @Override
    public boolean deleteById(long id) {
        return reservationDao.deleteById(id);
    }
}
