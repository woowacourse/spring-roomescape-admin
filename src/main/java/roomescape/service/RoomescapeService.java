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
        if (existsSameReservation(reservation)) {
            throw new IllegalArgumentException("[ERROR] 이미 존재하는 예약시간입니다.");
        }
        return roomescapeRepository.saveReservation(reservation);
    }

    public void removeReservation(final long id) {
        roomescapeRepository.deleteById(id);
    }

    private boolean existsSameReservation(final Reservation reservation) {
        List<Reservation> reservations = findReservations();
        boolean exists = false;
        for (Reservation candidate : reservations) {
            exists = candidate.isDuplicateReservation(reservation);
        }
        return exists;
    }
}
