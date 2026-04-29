package roomescape.domain.reservationtime;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.reservationtime.dto.CreateTimeRequest;
import roomescape.domain.reservationtime.dto.CreateTimeResponse;
import roomescape.domain.reservationtime.dto.ReservationTimeResponse;

@Service
@RequiredArgsConstructor
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public CreateTimeResponse createReservationTime(CreateTimeRequest request) {
        request.validate();
        ReservationTime reservationTime = reservationTimeRepository.save(request.toEntity());
        return CreateTimeResponse.from(reservationTime);
    }

    public List<ReservationTimeResponse> getAllReservationTime() {
        return reservationTimeRepository.findAll().stream()
            .map(ReservationTimeResponse::from)
            .toList();
    }

    public void deleteReservation(Long id) {
        int deletedCount = reservationTimeRepository.deleteById(id);
        if (deletedCount == 0) {
            throw new IllegalArgumentException("존재하지 않는 예약 시간대 입니다.");
        }
    }
}
