package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.Reservations;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ReservationService {
    @Autowired
    private final ReservationDao reservationDao;
    private final Reservations reservations;
    private final AtomicLong index = new AtomicLong(1);

    public ReservationService(Reservations reservations, ReservationDao reservationDao) {
        this.reservations = reservations;
        this.reservationDao = reservationDao;
    }

    public ReservationService() {
        this(new Reservations(), new ReservationDao());
    }

    public List<Reservation> getAllReservations() {
        return reservationDao.findAllReservation();
    }

    public ReservationResponseDto reserve(ReservationRequestDto reservationRequestDto) {
        Reservation reservation = createReservation(reservationRequestDto);
        addReservation(reservation);
        return reservation.toResponseDto();
    }

    public void deleteReservation(Long id) {
        reservations.delete(id);
    }

    private void addReservation(Reservation reservation) {
        reservations.add(reservation.getId(), reservation);
    }

    private Reservation createReservation(ReservationRequestDto reservationRequestDto) {
        return new Reservation(index.getAndIncrement(), reservationRequestDto);
    }
}
