package roomescape.application;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimeRepository;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository repository;

    public ReservationTimeService(ReservationTimeRepository repository) {
        this.repository = repository;
    }

    public ReservationTimeResponse create(ReservationTimeRequest request) {
        ReservationTime reservationTime = repository.create(request.from());
        return ReservationTimeResponse.from(reservationTime);
    }

    public List<ReservationTimeResponse> findAll() {
        List<ReservationTime> reservationTimes = repository.findAll();
        return convertToReservationTimeResponses(reservationTimes);
    }

    private List<ReservationTimeResponse> convertToReservationTimeResponses(List<ReservationTime> reservationTimes) {
        return reservationTimes.stream()
                .map(ReservationTimeResponse::from)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        Optional<ReservationTime> findReservationTime = repository.findById(id);
        if (findReservationTime.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 예약 시간 입니다.");
        }
        repository.deleteById(id);
    }
}
