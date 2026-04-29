package roomescape.reservation.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import roomescape.reservation.controller.dto.ReservationResponseDto;
import roomescape.reservation.controller.dto.ReservationSaveRequestDto;
import roomescape.controller.dto.ReservationTimeResponseDto;
import roomescape.controller.dto.ReservationTimeSaveRequestDto;
import roomescape.reservation.service.RoomescapeService;

@RestController
public class ReservationController {
    private final RoomescapeService roomescapeService;

    public ReservationController(RoomescapeService roomescapeService) {
        this.roomescapeService = roomescapeService;
    }

    @GetMapping("/reservations")
    public List<ReservationResponseDto> getReservations() {
        return roomescapeService.getReservations().stream()
                .map(ReservationResponseDto::from)
                .collect(Collectors.toList());
    }

    @PostMapping("/reservations")
    public ReservationResponseDto saveReservation(@RequestBody ReservationSaveRequestDto reservationRequest) {
        return ReservationResponseDto.from(roomescapeService.save(reservationRequest.toServiceDto()));
    }

    @DeleteMapping("/reservations/{id}")
    public boolean deleteReservation(@PathVariable long id) {
        return roomescapeService.deleteById(id);
    }
}