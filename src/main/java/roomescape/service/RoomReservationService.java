package roomescape.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import roomescape.domain.RoomReservation;
import roomescape.entity.RoomReservationEntity;

@Service
public class RoomReservationService {

    private final Map<Long, RoomReservationEntity> reservationStorage;

    public RoomReservationService() {
        this.reservationStorage = new HashMap<>();
    }

    public List<RoomReservation> findAllRoomReservations() {
        return reservationStorage.values().stream()
                .map(entity ->
                        new RoomReservation(entity.getId(), entity.getName(), entity.getDate(), entity.getTime())
                )
                .toList();
    }
}
