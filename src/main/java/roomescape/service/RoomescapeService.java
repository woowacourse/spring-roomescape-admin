package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.repository.RoomescapeRepository;

@Service
public class RoomescapeService {

    private final RoomescapeRepository roomescapeRepository;

    public RoomescapeService(final RoomescapeRepository roomescapeRepository) {
        this.roomescapeRepository = roomescapeRepository;
    }

    public List<ReservationResponse> findReservations() {
        List<Reservation> reservations = roomescapeRepository.findAll();
        return reservations.stream().map(ReservationResponse::of).toList();
    }

    public ReservationResponse addReservation(final ReservationRequest request) {
        Reservation reservation = request.toReservation();
        if (existsSameReservation(reservation)) {
            throw new IllegalArgumentException("[ERROR] 이미 존재하는 예약시간입니다.");
        }
        Reservation saved = roomescapeRepository.saveReservation(reservation);
        return ReservationResponse.of(saved);
    }

    public void removeReservation(final long id) {
        int deleteCounts = roomescapeRepository.deleteById(id);
        if (deleteCounts == 0) {
            throw new IllegalArgumentException(String.format("[ERROR] 예약번호 %d번은 존재하지 않습니다.", id));
        }
    }

    private boolean existsSameReservation(final Reservation reservation) {
        List<Reservation> reservations = roomescapeRepository.findAll();
        boolean exists = false;
        for (Reservation candidate : reservations) {
            exists = candidate.isDuplicateReservation(reservation);
        }
        return exists;
    }

}
