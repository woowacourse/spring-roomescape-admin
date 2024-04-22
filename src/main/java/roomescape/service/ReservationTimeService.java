package roomescape.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationTimeCreateRequest;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTime> findReservationTimes() {
        return reservationTimeRepository.findReservationTimes();
    }

    public Long createReservationTime(ReservationTimeCreateRequest reservationTimeCreateRequest) {
        return reservationTimeRepository.createReservationTime(reservationTimeCreateRequest);
    }

    public Optional<ReservationTime> findReservationTimeById(Long createdReservationTimeId) {
        return reservationTimeRepository.findReservationTimeById(createdReservationTimeId);
    }

    public void deleteReservationTimeById(Long id) {
        reservationTimeRepository.deleteReservationTimeById(id);
    }
}
