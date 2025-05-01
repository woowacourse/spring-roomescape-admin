package roomescape.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import roomescape.Repository.ReservationTimeRepository;
import roomescape.controller.dto.response.ReservationTimeResponse;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.service.dto.ReservationTimeCreation;

@Service
public class ReservationTimeService {

    private static final String ERROR_SIGN = "[ERROR] ";
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTimeResponse create(ReservationTimeCreation creation) {
        ReservationTime reservationTime = ReservationTime.from(creation);

        validateDuplicateExistedTime(reservationTime);

        Long id = reservationTimeRepository.insert(reservationTime);
        ReservationTime insertedReservationTime = reservationTimeRepository.findBy(id);

        return ReservationTimeResponse.from(insertedReservationTime);
    }

    private void validateDuplicateExistedTime(final ReservationTime reservationTime) {
        if (isReservationTimeExist(reservationTime)) {
            throw new IllegalArgumentException(ERROR_SIGN + " 이미 존재하는 예약 시간이 있습니다.");
        }
    }

    private boolean isReservationTimeExist(ReservationTime time) {
        return reservationTimeRepository.existsBy(time.getStartAt());
    }

    public List<ReservationTimeResponse> readAll() {
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();
        return reservationTimes.stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public void deleteBy(final Long id) {
        if (!reservationTimeRepository.deleteBy(id)) {
            throw new NoSuchElementException(ERROR_SIGN + " 해당 ID의 시간을 찾을 수 없습니다. id:" + id);
        }
    }
}
