package roomescape.reservation.service;

import java.util.List;

import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationTime;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.reservation.service.dto.ReservationSaveServiceDto;

@Service
public class RoomescapeServiceImpl implements RoomescapeService {

    private final ReservationRepository reservationRepository;

    public RoomescapeServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation save(ReservationSaveServiceDto reservation) {
        Reservation newReservation = new Reservation(
                reservation.getName(),
                reservation.getDate(),
                new ReservationTime(reservation.getTimeId(), null)
        );
        return reservationRepository.save(newReservation);
    }

    @Override
    public boolean deleteById(long id) {
        return reservationRepository.deleteById(id);
    }

    @Override
    public List<ReservationTime> getReservationTimes() {
        return reservationRepository.findReservationTimes();
    }

    @Override
    public ReservationTime saveReservationTime(ReservationTime reservationTime) {
        return reservationRepository.saveReservationTime(reservationTime);
    }

    @Override
    public boolean deleteReservationTimeById(long id) {
        return reservationRepository.deleteReservationTimeById(id);
    }
}