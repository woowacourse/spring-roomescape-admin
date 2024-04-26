package roomescape.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationTimeCreateRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTimeResponse> findAll() {
        return reservationTimeRepository.findAll()
                .stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public ReservationTimeResponse create(ReservationTimeCreateRequest reservationTimeCreateRequest) {
        return ReservationTimeResponse.from(reservationTimeRepository.save(reservationTimeCreateRequest));
    }

    public void delete(Long id) {
        if (reservationTimeRepository.deleteById(id) == 0) {
            throw new NoSuchElementException("삭제할 예약 시간이 존재하지 않습니다");
        }
    }
}
