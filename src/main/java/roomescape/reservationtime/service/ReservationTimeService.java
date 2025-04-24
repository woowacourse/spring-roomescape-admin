package roomescape.reservationtime.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservationtime.domain.ReservationTime;
import roomescape.reservationtime.dto.ReservationTimeRequest;
import roomescape.reservationtime.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTime> getReservationTimes() {
        return reservationTimeRepository.findAll();
    }

    public ReservationTime createReservationTime(ReservationTimeRequest request) {
        ReservationTime reservationTime = ReservationTime.createWithoutId(request.startAt());

        for (ReservationTime existReservationTime : reservationTimeRepository.findAll()) {
            validateDuplicateTime(existReservationTime, reservationTime);
        }

        return reservationTimeRepository.save(reservationTime);
    }

    public void deleteReservationTime(Long id) {
        reservationTimeRepository.deleteById(id);
    }

    private void validateDuplicateTime(ReservationTime existReservationTime, ReservationTime reservationTime) {
        if (existReservationTime.isStartAtEqualTo(reservationTime)) {
            throw new IllegalArgumentException("이미 존재하는 시간입니다.");
        }
    }
}
