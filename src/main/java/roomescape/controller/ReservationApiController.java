package roomescape.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.entity.ReservationEntity;

@Controller
@RequestMapping("/reservations")
public class ReservationApiController {

    private final List<ReservationEntity> reservationEntities;

    public ReservationApiController(List<ReservationEntity> reservationEntities) {
        this.reservationEntities = reservationEntities;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> getReservations() {
        List<ReservationResponseDto> reservationResponseDtos = reservationEntities.stream()
                .map(ReservationResponseDto::new)
                .toList();

        return new ResponseEntity<>(reservationResponseDtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> createReservation(
            @RequestBody ReservationRequestDto reservationRequestDto) {

        ReservationEntity reservation = reservationRequestDto.toEntity();
        reservationEntities.add(reservation);
        return new ResponseEntity<>(new ReservationResponseDto(reservation), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationEntities.removeIf(reservationEntity -> reservationEntity.getId().equals(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
