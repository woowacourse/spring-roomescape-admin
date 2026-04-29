package roomescape.mapper;

import roomescape.domain.RoomReservation;
import roomescape.dto.CreateRoomReservationDto;
import roomescape.dto.RoomReservationResultDto;
import roomescape.repository.entity.RoomReservationEntity;

public class RoomReservationMapper {

    private RoomReservationMapper() {}

    public static RoomReservation toRoomReservation(CreateRoomReservationDto base) {
        return new RoomReservation(base.getName(), base.getDate(), base.getTime());
    }

    public static RoomReservationEntity toRoomReservationEntity(Long id, RoomReservation roomReservation) {
        return new RoomReservationEntity(id, roomReservation.getName(), roomReservation.getDate(),
                roomReservation.getTime());
    }

    public static RoomReservationResultDto toRoomReservationResultDto(Long id, RoomReservation roomReservation) {
        return new RoomReservationResultDto(id, roomReservation.getName(), roomReservation.getDate(),
                roomReservation.getTime());
    }
}
