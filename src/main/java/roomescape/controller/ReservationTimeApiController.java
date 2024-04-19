package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.entity.ReservationTime;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeApiController {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeApiController(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @GetMapping
    public List<ReservationTimeResponseDto> getReservationTimes() {
        return reservationTimeDao.findReservationTimes().stream()
                .map(ReservationTimeResponseDto::from)
                .toList();
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponseDto> postReservationTime(@RequestBody ReservationTimeRequestDto requestDto) {
        ReservationTime reservationTime = reservationTimeDao.createReservationTime(requestDto.toEntity());
        URI location = UriComponentsBuilder.newInstance()
                .path("/times/{id}")
                .buildAndExpand(reservationTime.getId())
                .toUri();

        return ResponseEntity.created(location).body(ReservationTimeResponseDto.from(reservationTime));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        reservationTimeDao.removeReservationTime(id);
        return ResponseEntity.noContent().build();
    }
}
