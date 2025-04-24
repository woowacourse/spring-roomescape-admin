package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import roomescape.dto.ReservationTimeReqDto;
import roomescape.dto.ReservationTimeResDto;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    @Autowired
    ReservationTimeService reservationTimeService;

    @GetMapping
    private ResponseEntity<List<ReservationTimeResDto>> readAll() {
        List<ReservationTimeResDto> response = reservationTimeService.findAllReservationTimes();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    private ResponseEntity<ReservationTimeResDto> create(@RequestBody ReservationTimeReqDto dto, UriComponentsBuilder ucb) {
        ReservationTimeResDto newReservationTime = reservationTimeService.addAndGet(dto);
        URI uri = ucb.path("/times/{id}").buildAndExpand(newReservationTime.id()).toUri();
        return ResponseEntity.created(uri).body(newReservationTime);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationTimeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
