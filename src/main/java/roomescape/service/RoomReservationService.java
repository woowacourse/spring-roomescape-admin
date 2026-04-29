package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.RoomReservation;
import roomescape.dto.RoomReservationResultDto;
import roomescape.mapper.RoomReservationMapper;
import roomescape.repository.RoomReservationRepository;

@Service
public class RoomReservationService {

    private final RoomReservationRepository roomReservationRepository;

    public RoomReservationService(RoomReservationRepository roomReservationRepository) {
        this.roomReservationRepository = roomReservationRepository;
    }

    public List<RoomReservationResultDto> findAllRoomReservations() {
        return roomReservationRepository.findAll().stream()
                .map(entity ->
                        new RoomReservationResultDto(entity.getId(), entity.getName(), entity.getDate(),
                                entity.getTime())
                )
                .toList();
    }

    public RoomReservationResultDto reserve(RoomReservation roomReservation) {
        Long roomReservationId = roomReservationRepository.save(roomReservation);
        return RoomReservationMapper.toRoomReservationResultDto(roomReservationId, roomReservation);
    }
}
