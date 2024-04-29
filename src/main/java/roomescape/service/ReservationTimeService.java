package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.model.ReservationTimeDto;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTimeDto> findAll() {
        return reservationTimeRepository.findAllReservationTimes();
    }

    public ReservationTimeDto save(final ReservationTimeDto reservationTimeRequest) {
        return reservationTimeRepository.createReservationTime(reservationTimeRequest);
    }

    public void delete(final Long id) {
        reservationTimeRepository.deleteReservationTime(id);
    }
}
