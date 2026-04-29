package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.QueryingDao;
import roomescape.dao.UpdatingDao;
import roomescape.dto.ReservationRequestDto;
import roomescape.entity.Reservation;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final QueryingDao queryingDao;
    private final UpdatingDao updatingDao;

    public ReservationController(QueryingDao queryingDao, UpdatingDao updatingDao) {
        this.queryingDao = queryingDao;
        this.updatingDao = updatingDao;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> readAll() {
        return ResponseEntity.ok(queryingDao.findAll());
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody ReservationRequestDto requestDto) {
        Long id = updatingDao.insertWithKeyHolder(requestDto);
        Reservation savedReservation = queryingDao.findById(id);
        return ResponseEntity.ok(savedReservation);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        updatingDao.delete(id);
    }
}
