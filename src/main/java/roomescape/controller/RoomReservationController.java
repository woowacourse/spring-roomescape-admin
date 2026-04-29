package roomescape.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.CreateRoomReservationDto;
import roomescape.dto.RoomReservationResultDto;
import roomescape.mapper.RoomReservationMapper;
import roomescape.service.RoomReservationService;

@RestController
@RequestMapping("/reservations")
public class RoomReservationController {

    private final RoomReservationService roomReservationService;

    public RoomReservationController(RoomReservationService roomReservationService) {
        this.roomReservationService = roomReservationService;
    }

    @GetMapping
    public ResponseEntity<List<RoomReservationResultDto>> getRoomReservations() {
        List<RoomReservationResultDto> reservations = roomReservationService.findAllRoomReservations();
        return ResponseEntity.ok(reservations);
    }

    @PostMapping
    public ResponseEntity<RoomReservationResultDto> createRoomReservations(@RequestBody CreateRoomReservationDto createRoomReservationDto) {
        RoomReservationResultDto reservedRoomId = roomReservationService.reserve(
                RoomReservationMapper.toRoomReservation(createRoomReservationDto));
        return ResponseEntity.of(Optional.of(reservedRoomId));
    }
}
