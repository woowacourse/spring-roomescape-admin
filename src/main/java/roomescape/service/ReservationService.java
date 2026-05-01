package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.service.dto.ReservationCreateCommand;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<Reservation> findAllReservations() {
        return reservationDao.findAll();
    }

    public Reservation createReservation(ReservationCreateCommand command) {
        ReservationTime time = reservationTimeDao.findById(command.timeId());

        Reservation reservation = new Reservation(
                null,
                command.name(),
                command.date(),
                time
        );

        Long generatedId = reservationDao.save(reservation);
        reservation.setId(generatedId);

        return reservation;
    }

    public void deleteReservation(Long id) {
        reservationDao.deleteById(id);
    }
}
