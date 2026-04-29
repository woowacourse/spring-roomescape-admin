package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationCommand;
import roomescape.domain.ReservationTime;
import roomescape.exception.ErrorMessage;
import roomescape.exception.NotFoundResourceException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@Service
public class RoomReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public RoomReservationService(ReservationRepository reservationRepository, ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<Reservation> getAllReservation() {
        return reservationRepository.getAllReservation();
    }

    @Transactional
    public Reservation addReservation(ReservationCommand reservationCommand) {
        ReservationTime reservationTime = reservationTimeRepository.getReservationTime(reservationCommand.timeId())
                .orElseThrow(() -> new NotFoundResourceException(ErrorMessage.RESERVATION_TIME_NOT_FOUND));

        return reservationRepository.addReservation(reservationCommand, reservationTime);
    }

    public void deleteReservation(long id) {
        int deletedCount = reservationRepository.deleteReservation(id);

        if(deletedCount == 0) {
            throw new NotFoundResourceException(ErrorMessage.RESERVATION_NOT_FOUND);
        }
    }
}
