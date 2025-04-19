package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.repository.RoomescapeRepository;

@Service
public class RoomescapeService {

    private final RoomescapeRepository roomescapeRepository;

    public RoomescapeService(final RoomescapeRepository roomescapeRepository) {
        this.roomescapeRepository = roomescapeRepository;
    }

    public List<Reservation> findReservations() {
        return roomescapeRepository.findAll();
    }

    public Reservation addReservation(final Reservation reservation) {
        return roomescapeRepository.saveReservation(reservation);
    }

    public void removeReservation(final long id) {
        roomescapeRepository.deleteById(id);
    }
}
