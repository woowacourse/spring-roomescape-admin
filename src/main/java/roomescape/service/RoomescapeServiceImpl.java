package roomescape.service;

import java.util.List;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
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
                reservation.getTime()
        );
        return roomescapeRepository.save(newReservation);
    }

    @Override
    public boolean deleteById(long id) {
        return roomescapeRepository.deleteById(id);
    }
}