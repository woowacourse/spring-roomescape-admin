package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.ReservationTime.ReservationTime;
import roomescape.domain.ReservationTime.ReservationTimeCommand;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTime> getAllReservationTime() {
        return reservationTimeRepository.getAllReservationTime();
    }

    @Transactional
    public ReservationTime addReservationTime(ReservationTimeCommand reservationTimeCommand) {
        return reservationTimeRepository.addReservationTime(reservationTimeCommand);
    }

    public void deleteReservationTime(long id) {
        reservationTimeRepository.deleteReservationTime(id);
    }
}
