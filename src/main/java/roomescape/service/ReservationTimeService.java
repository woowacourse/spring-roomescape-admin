package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.controller.ReservationTimeCreateRequest;
import roomescape.entity.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(final ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTime> getAllReservationTimes() {
        return reservationTimeRepository.findAll();
    }

    public ReservationTime addReservationTime(ReservationTimeCreateRequest reservationTimeCreateRequest) {
        Long id = reservationTimeRepository.add(reservationTimeCreateRequest);
        return reservationTimeRepository.findById(id);
    }

    public void deleteReservationTimeById(Long id) {
        reservationTimeRepository.deleteById(id);
    }
}
