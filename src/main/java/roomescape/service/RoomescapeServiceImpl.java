package roomescape.service;

import java.util.List;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.RoomescapeRepository;
import roomescape.service.dto.ReservationSaveServiceDto;

@Service
public class RoomescapeServiceImpl implements RoomescapeService {

    private final RoomescapeRepository roomescapeRepository;

    public RoomescapeServiceImpl(RoomescapeRepository roomescapeRepository) {
        this.roomescapeRepository = roomescapeRepository;
    }

    @Override
    public List<Reservation> getReservations() {
        return roomescapeRepository.findAll();
    }

    @Override
    public Reservation save(ReservationSaveServiceDto reservation) {
        Reservation newReservation = new Reservation(
                reservation.getName(),
                reservation.getDate(),
                new ReservationTime(reservation.getTimeId(), null)
        );
        return roomescapeRepository.save(newReservation);
    }

    @Override
    public boolean deleteById(long id) {
        return roomescapeRepository.deleteById(id);
    }

    @Override
    public List<ReservationTime> getReservationTimes() {
        return roomescapeRepository.findReservationTimes();
    }

    @Override
    public ReservationTime saveReservationTime(ReservationTime reservationTime) {
        return roomescapeRepository.saveReservationTime(reservationTime);
    }

    @Override
    public boolean deleteReservationTimeById(long id) {
        return roomescapeRepository.deleteReservationTimeById(id);
    }
}