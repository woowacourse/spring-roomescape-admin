package roomescape.reservationtime.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservationtime.domain.ReservationTime;
import roomescape.reservationtime.dto.repository.ReservationTimeRepository;
import roomescape.reservationtime.dto.request.ReservationTimeCreateRequest;
import roomescape.reservationtime.dto.response.ReservationTimeResponse;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(final ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTimeResponse> getReservationTimes() {
        return reservationTimeRepository.getAll().stream()
                .map(reservation -> ReservationTimeResponse.from(reservationTimeRepository.getCachedId(reservation),
                        reservation))
                .toList();
    }

    public void delete(long id) {
        reservationTimeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("요청한 id와 일치하는 예약 시간 정보가 없습니다."));
        reservationTimeRepository.deleteById(id);
    }

    public ReservationTimeResponse create(final ReservationTimeCreateRequest request) {
        validateIsTimeUnique(request);
        ReservationTime newReservationTime = reservationTimeRepository.put(request.toReservationTime());
        long newId = reservationTimeRepository.getCachedId(newReservationTime);
        return ReservationTimeResponse.from(newId, newReservationTime);
    }

    private void validateIsTimeUnique(final ReservationTimeCreateRequest request) {
        if (reservationTimeRepository.checkExistsByStartAt(request.startAt())) {
            throw new IllegalArgumentException("중복된 예약 시간을 생성할 수 없습니다");
        }
    }
}
