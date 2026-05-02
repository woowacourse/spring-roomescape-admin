package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository repository;
    private final ReservationRepository reservationRepository;

    public ReservationTimeService(ReservationTimeRepository repository, ReservationRepository reservationRepository) {
        this.repository = repository;
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationTime> getAll() {
        return repository.getAll();
    }

    public ReservationTime add(ReservationTimeRequest request) {
        ReservationTime reservationTime = new ReservationTime(request.startAt());
        return repository.save(reservationTime);
    }

    public void deleteById(long id) {
        if (reservationRepository.existByReservationTimeId(id)) {
            throw new IllegalArgumentException("해당 시간을 사용하는 예약이 존재합니다.");
        }
        repository.deleteById(id);
    }
}
