package roomescape.reservation;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.time.Time;
import roomescape.time.TimeDao;

@Service
public class ReservationService {

    private final ReservationDao reservationDAO;
    private final TimeDao timeDao;

    public ReservationService(
            @Autowired final ReservationDao reservationDAO,
            @Autowired final TimeDao timeDao
    ) {
        this.reservationDAO = reservationDAO;
        this.timeDao = timeDao;
    }

    public ReservationResponse createReservation(final ReservationRequest reservationRequest) {
        validateExistTimeById(reservationRequest.timeId());

        final Reservation notSavedReservation = new Reservation(
                null,
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.timeId()
        );
        final Time time = timeDao.findTimeById(reservationRequest.timeId());
        final Reservation savedReservation = reservationDAO.saveReservation(notSavedReservation);
        return ReservationResponse.createResponse(savedReservation, time);
    }

    private void validateExistTimeById(final Long timeId) {
        if (!timeDao.existTimeById(timeId)) {
            throw new IllegalArgumentException("[ERROR]");
        }
    }

    public List<ReservationResponse> findAllReservation() {
        return reservationDAO.findAllReservation().stream()
                .map(reservation -> {
                    final Time time = timeDao.findTimeById(reservation.timeId());
                    return ReservationResponse.createResponse(reservation, time);
                })
                .toList();
    }

    public void deleteReservationById(final Long id) {
        this.reservationDAO.deleteReservationById(id);
    }
}
