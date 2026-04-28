package roomescape.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import roomescape.controller.dto.ReservationSaveRequestDto;
import roomescape.controller.dto.ReservationResponseDto;
import roomescape.controller.dto.ReservationsResponseDto;
import roomescape.service.RoomescapeService;

@RestController
public class RoomescapeController {
    private final RoomescapeService roomescapeService;

    public RoomescapeController(RoomescapeService roomescapeService) {
        this.roomescapeService = roomescapeService;
    }

    @GetMapping("/reservations")
    public ReservationsResponseDto getReservations() { 
        List<ReservationResponseDto> reservations = roomescapeService.getReservations().stream()
                .map(ReservationResponseDto::from)
                .collect(Collectors.toList());
        return new ReservationsResponseDto(reservations);
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