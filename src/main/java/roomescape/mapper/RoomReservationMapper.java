package roomescape.mapper;

import roomescape.domain.RoomReservation;
import roomescape.dto.CreateRoomReservationDto;
import roomescape.dto.RoomReservationResultDto;
import roomescape.repository.entity.RoomReservationEntity;

public class RoomReservationMapper {

    private RoomReservationMapper() {
    }

    public static RoomReservation toRoomReservation(CreateRoomReservationDto base) {
        return new RoomReservation(base.getName(), base.getDate(), base.getTime());
    }

    public static RoomReservation toRoomReservation(RoomReservationEntity entity) {
        return new RoomReservation(entity.getId(), entity.getName(), entity.getDate(), entity.getTime());
    }

    public static RoomReservationResultDto toRoomReservationResultDto(RoomReservation roomReservation) {
        return new RoomReservationResultDto(roomReservation.getId(), roomReservation.getName(),
                roomReservation.getDate(),
                roomReservation.getTime());
    }
}
