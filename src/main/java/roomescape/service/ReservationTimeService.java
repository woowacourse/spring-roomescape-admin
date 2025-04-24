package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTime> getAll() {
        return reservationTimeRepository.findAll();
    }

    public void validateDuplicateStartTime(LocalTime startAt) {
        if (reservationTimeRepository.existByStartAt(startAt)) {
            throw new IllegalArgumentException("이미 존재하는 예약 시간입니다.");
        }
    }

    public ReservationTime addAndGet(LocalTime startAt) {
        ReservationTime reservationTime = new ReservationTime(startAt);
        return reservationTimeRepository.insertAndGet(reservationTime);
    }

    public void deleteById(Long id) {
        int affectedRows = reservationTimeRepository.deleteByIdAndCountAffected(id);
        if (affectedRows == 0) {
            throw new IllegalArgumentException("존재하지 않는 예약 시간 id입니다.");
        }
    }

    public ReservationTime getById(Long timeId) {
        return reservationTimeRepository.findById(timeId);
    }
}
