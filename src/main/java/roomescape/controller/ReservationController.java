package roomescape.controller;

import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;

import roomescape.service.ReservationService;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> getReservations() {
        final List<ReservationResponseDto> reservationResponseDtos = reservationService.getAllReservations();

        return new ResponseEntity<>(reservationResponseDtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> add(
        @RequestBody final ReservationRequestDto reservationRequestDto) {
        final ReservationResponseDto reservationResponseDto =
            reservationService.createReservation(reservationRequestDto);

        return new ResponseEntity<>(reservationResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final long id) {
        reservationService.removeReservation(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
