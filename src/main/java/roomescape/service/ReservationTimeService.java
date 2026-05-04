package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.service.dto.ReservationTimeCreateCommand;

@Service
@Transactional(readOnly = true)
public class ReservationTimeService {
    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTime> findAll() {
        return reservationTimeDao.findAll();
    }

    @Transactional
    public ReservationTime create(ReservationTimeCreateCommand command) {
        if (reservationTimeDao.existsByStartAt(command.startAt())) {
            throw new IllegalArgumentException("이미 존재하는 예약 시간입니다.");
        }
        Long generatedId = reservationTimeDao.save(command.startAt());
        return ReservationTime.from(generatedId, command.startAt());
    }

    @Transactional
    public void delete(Long id) {
        int affectedRows = reservationTimeDao.deleteById(id);
        if (affectedRows == 0) {
            throw new IllegalArgumentException("이미 삭제되었거나 존재하지 않는 예약 시간입니다.");
        }
    }
}
