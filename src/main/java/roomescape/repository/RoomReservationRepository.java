package roomescape.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.domain.RoomReservation;
import roomescape.mapper.RoomReservationMapper;
import roomescape.repository.dao.RoomReservationDao;

@Repository
public class RoomReservationRepository {

    private final RoomReservationDao roomReservationDao;

    public RoomReservationRepository(RoomReservationDao roomReservationDao) {
        this.roomReservationDao = roomReservationDao;
    }

    public List<RoomReservation> findAll() {
        return roomReservationDao.selectAll().stream()
                .map(RoomReservationMapper::toRoomReservation)
                .toList();
    }

    public RoomReservation save(RoomReservation roomReservation) {
        Long id = roomReservationDao.insert(roomReservation);
        return new RoomReservation(id, roomReservation.getName(), roomReservation.getDate(), roomReservation.getTime());
    }

    public void delete(Long id) {
        int deletedCount = roomReservationDao.deleteById(id);

        if (deletedCount == 0) {
            throw new IllegalArgumentException("존재하지 않는 예약 번호입니다.");
        }
    }
}
