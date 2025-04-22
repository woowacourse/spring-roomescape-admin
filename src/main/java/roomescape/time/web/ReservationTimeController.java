package roomescape.time.web;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.time.ReservationTime;
import roomescape.time.ReservationTimeDao;

@Controller
public class ReservationTimeController {
    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeController(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @GetMapping("/times")
    @ResponseBody
    public List<ReservationTimeResponse> getAll() {
        return reservationTimeDao.findAll()
                .stream()
                .map(ReservationTimeResponse::new)
                .toList();
    }

    @PostMapping("/times")
    @ResponseBody
    public ReservationTimeResponse create(@RequestBody ReservationTimeRequest request) {
        ReservationTime savedReservation = reservationTimeDao.save(request.toReservationTime());
        return new ReservationTimeResponse(savedReservation);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        System.out.println(id);
        boolean isRemoved = reservationTimeDao.removeById(id);
        if (isRemoved) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
