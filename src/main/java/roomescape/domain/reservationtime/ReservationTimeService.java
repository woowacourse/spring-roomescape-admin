package roomescape.domain.reservationtime;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.reservation.ReservationRepository;
import roomescape.domain.reservationtime.dto.CreateTimeRequest;
import roomescape.domain.reservationtime.dto.CreateTimeResponse;
import roomescape.domain.reservationtime.dto.ReservationTimeResponse;
import roomescape.support.RoomescapeErrorCode;
import roomescape.support.RoomescapeException;

@Service
@RequiredArgsConstructor
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;
    private final ReservationRepository reservationRepository;

    public CreateTimeResponse createReservationTime(CreateTimeRequest request) {
        ReservationTime reservationTime = reservationTimeRepository.save(request.toEntity());
        return CreateTimeResponse.from(reservationTime);
    }

    public List<ReservationTimeResponse> getAllReservationTime() {
        return reservationTimeRepository.findAll().stream()
            .map(ReservationTimeResponse::from)
            .toList();
    }

    public void deleteReservationTime(Long id) {
        if (reservationRepository.countByTimeId(id) > 0) {
            throw new RoomescapeException(RoomescapeErrorCode.RESERVATION_TIME_IN_USE);
        }
        int deletedCount = reservationTimeRepository.deleteById(id);
        if (deletedCount == 0) {
            throw new RoomescapeException(RoomescapeErrorCode.RESERVATION_TIME_NOT_EXIST);
        }
    }
}
