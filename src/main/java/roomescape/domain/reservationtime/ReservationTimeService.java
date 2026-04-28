package roomescape.domain.reservationtime;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.reservationtime.dto.CreateTimeRequest;
import roomescape.domain.reservationtime.dto.CreateTimeResponse;

@Service
@RequiredArgsConstructor
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public CreateTimeResponse createReservationTime(CreateTimeRequest request) {
        request.validate();
        ReservationTime reservationTime = reservationTimeRepository.save(request.toEntity());
        return CreateTimeResponse.from(reservationTime);
    }
}
