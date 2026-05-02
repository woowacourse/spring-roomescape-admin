package roomescape.service;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.ReservationTime.ReservationTime;
import roomescape.domain.ReservationTime.ReservationTimeCommand;
import roomescape.exception.DataReferencedException;
import roomescape.exception.ErrorMessage;
import roomescape.repository.reservationTime.ReservationTimeRepository;

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
        try {
            reservationTimeRepository.deleteReservationTime(id);
        }  catch(
        DataIntegrityViolationException e) {
            throw new DataReferencedException(ErrorMessage.CANNOT_DELETE_RESERVATION_TIME_IN_USE);
        }
    }
}
