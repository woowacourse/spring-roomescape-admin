package roomescape.service;

import java.util.List;
import roomescape.dto.CreateReservationTimeDto;
import roomescape.entity.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTime createReservationTime(CreateReservationTimeDto request) {
        Long id = reservationTimeRepository.addAndGetId(request);
        return reservationTimeRepository.findById(id);
    }

    public List<ReservationTime> getAllReservationTimes() {
        return reservationTimeRepository.findAll();
    }

    public void deleteReservationTime(Long id) {
        reservationTimeRepository.deleteById(id);
    }
}
