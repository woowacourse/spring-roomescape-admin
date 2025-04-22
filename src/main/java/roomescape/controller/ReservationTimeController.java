package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import roomescape.dao.QueryingDAO;
import roomescape.dao.UpdatingDAO;
import roomescape.dto.ReservationTimeReqDto;
import roomescape.dto.ReservationTimeResDto;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    @Autowired
    QueryingDAO queryingDAO;

    @Autowired
    UpdatingDAO updatingDAO;

    @GetMapping
    private ResponseEntity<List<ReservationTimeResDto>> readAll() {
        List<ReservationTimeResDto> response = queryingDAO.findAllReservationTimes();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    private ResponseEntity<ReservationTimeResDto> create(@RequestBody ReservationTimeReqDto dto, UriComponentsBuilder ucb) {
        ReservationTimeResDto newReservationTime = updatingDAO.addAndGet2(dto);
        URI uri = ucb.path("/times/{id}").buildAndExpand(newReservationTime.id()).toUri();
        return ResponseEntity.created(uri).body(newReservationTime);
    }
}
