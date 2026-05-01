package roomescape.reservation;

import org.springframework.stereotype.Service;
import roomescape.reservation.dto.ReservationCreateRequestDto;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.reservation.time.ReservationTime;
import roomescape.reservation.time.repository.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository, ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation save(ReservationCreateRequestDto request) {
        ReservationTime time = reservationTimeRepository.findById(request.timeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 시간입니다. timeId=" + request.timeId()));

        return reservationRepository.save(request.toEntity(time));
    }

    public void deleteById(long id) {
        int deleteCount = reservationRepository.deleteById(id);
        if (deleteCount == 0) {
            throw new IllegalArgumentException("존재하지 않는 예약 id 입니다. id = " + id);
        }
    }
}
