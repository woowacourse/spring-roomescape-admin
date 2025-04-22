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
import roomescape.dao.QueryingDAO;
import roomescape.dao.UpdatingDAO;
import roomescape.dto.ReservationReqDto;
import roomescape.dto.ReservationResDto;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private QueryingDAO queryingDAO;

    @Autowired
    private UpdatingDAO updatingDAO;

    @GetMapping
    public ResponseEntity<List<ReservationResDto>> readAll() {
        List<ReservationResDto> reservations = queryingDAO.findAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @PostMapping
    public ResponseEntity<ReservationResDto> create(@RequestBody ReservationReqDto dto, UriComponentsBuilder ucb) {
        ReservationResDto newReservation = updatingDAO.addAndGet(dto);
        URI uri = ucb.path("reservations/{id}").buildAndExpand(newReservation.id()).toUri();
        return ResponseEntity.created(uri).body(newReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        updatingDAO.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
