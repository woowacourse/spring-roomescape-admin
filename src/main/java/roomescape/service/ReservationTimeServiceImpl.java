package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.idgenerator.AutoIncrementIdGenerator;
import roomescape.idgenerator.IdGenerator;

@Service
public class ReservationTimeServiceImpl implements ReservationTimeService {
    private final ReservationTimeDao reservationTimeDao;
    private final IdGenerator idGenerator = new AutoIncrementIdGenerator();

    public ReservationTimeServiceImpl(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @Override
    public List<ReservationTimeResponse> findAll() {
        return reservationTimeDao.findAll()
                .stream()
                .map(ReservationTimeResponse::new)
                .toList();
    }

    @Override
    public ReservationTimeResponse save(ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = reservationTimeRequest.toDomain(
                idGenerator.generateNewId());
        reservationTimeDao.save(reservationTime);
        return new ReservationTimeResponse(reservationTime);
    }

    @Override
    public boolean deleteById(long id) {
        return reservationTimeDao.deleteById(id);
    }
}
