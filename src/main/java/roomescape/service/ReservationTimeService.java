package roomescape.service;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.entity.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(final ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTimeResponse createReservationTime(final ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = new ReservationTime(reservationTimeRequest.startAt());
        ReservationTime createdReservationTime = reservationTimeRepository.save(reservationTime);
        return ReservationTimeResponse.from(createdReservationTime);
    }

    public List<ReservationTimeResponse> getAllReservationTime() {
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();
        return ReservationTimeResponse.from(reservationTimes);
    }

    public void delete(long id) {
        int deletedRows = reservationTimeRepository.deleteById(id);
        if (deletedRows == 0) {
            throw new IllegalArgumentException("삭제할 예약 시간이 존재하지 않습니다.");
        }
    }

    public ReservationTimeResponse getReservationTime(@NotNull Long timeId) {
        ReservationTime reservationTime = reservationTimeRepository.findById(timeId);
        return ReservationTimeResponse.from(reservationTime);
    }
}
