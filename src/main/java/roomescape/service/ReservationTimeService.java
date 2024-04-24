package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.ReservationTimeDto;
import roomescape.entity.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTime createTime(ReservationTimeDto reservationTimeDto) {
        long timeId = reservationTimeRepository.create(reservationTimeDto);

        return makeTimeObject(reservationTimeDto, timeId);
    }

    public List<ReservationTime> readAllTime() {
        return reservationTimeRepository.readAll();
    }

    public void deleteTime(long id) {
        reservationTimeRepository.delete(id);
    }

    private ReservationTime makeTimeObject(ReservationTimeDto reservationTimeDto, long timeId) {
        LocalTime startAt = reservationTimeDto.startAt();
        return new ReservationTime(timeId, startAt);
    }
}
