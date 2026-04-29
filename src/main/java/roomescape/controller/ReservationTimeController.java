package roomescape.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.entity.ReservationTime;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
@RequiredArgsConstructor
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponseDto>> getReservationTimes() {
        final List<ReservationTimeResponseDto> reservationTimeResponseDtos =
            reservationTimeService.getAllReservationTimes();

        return new ResponseEntity<>(reservationTimeResponseDtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponseDto> add(@RequestBody final ReservationTimeRequestDto reservationTimeRequestDto) {
        final ReservationTimeResponseDto reservationTimeResponseDto =
            reservationTimeService.createReservationTime(reservationTimeRequestDto.startAt());

        return new ResponseEntity<>(reservationTimeResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final long id) {
        reservationTimeService.removeReservationTime(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
