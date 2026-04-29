package roomescape.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.domain.RoomReservation;
import roomescape.mapper.RoomReservationMapper;
import roomescape.repository.entity.RoomReservationEntity;

@Repository
public class RoomReservationRepository {

    private static final int ID_INCREMENT_UNIT = 1;

    private final Map<Long, RoomReservationEntity> reservationStorage;
    private final AtomicLong idSequence;

    public RoomReservationRepository() {
        this.reservationStorage = new HashMap<>();
        this.idSequence = new AtomicLong();
    }

    public List<RoomReservationEntity> findAll() {
        return reservationStorage.values().stream()
                .toList();
    }

    public Long save(RoomReservation roomReservation) {
        Long id = idSequence.addAndGet(ID_INCREMENT_UNIT);
        RoomReservationMapper.toRoomReservationEntity(id, roomReservation);
        reservationStorage.put(id, RoomReservationMapper.toRoomReservationEntity(id, roomReservation));
        return id;
    }
}
