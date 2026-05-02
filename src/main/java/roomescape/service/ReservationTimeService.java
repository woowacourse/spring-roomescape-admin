package roomescape.service;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTime create(String startAt) {
        ReservationTime reservationTime = ReservationTime.from(null, startAt);

        try {
            return reservationTimeRepository.save(reservationTime);
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("[ERROR] 이미 존재하는 시간입니다.", e);
        }
    }

    public List<ReservationTime> findAll() {
        return reservationTimeRepository.findAll();
    }

    public void deleteById(Long id) {
        try {
            reservationTimeRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("[ERROR] 예약에 사용 중인 시간은 삭제할 수 없습니다.", e);
        }
    }
}
