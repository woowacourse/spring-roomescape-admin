package roomescape.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import roomescape.dao.QueryingDAO;
import roomescape.dao.UpdatingDAO;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationCreateRequest;
import roomescape.dto.ReservationCreateResponse;
import roomescape.dto.TimeCreateRequest;
import roomescape.dto.TimeCreateResponse;

@Controller
public class ReservationController {

    private final JdbcTemplate jdbcTemplate;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        QueryingDAO dao = new QueryingDAO(jdbcTemplate);
        return ResponseEntity.ok(dao.findAllReservations());
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationCreateResponse> create(
            @RequestBody ReservationCreateRequest request
    ) {
        UpdatingDAO dao = new UpdatingDAO(jdbcTemplate);
        Reservation newReservation = new Reservation(request.name(), request.date(), request.time());
        Long id = dao.insertWithKeyHolder(newReservation);
        ReservationCreateResponse response = new ReservationCreateResponse(
                id,
                newReservation.getName(),
                newReservation.getDate(),
                newReservation.getTime());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        UpdatingDAO dao = new UpdatingDAO(jdbcTemplate);
        dao.delete(Long.valueOf(id));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/times")
    public ResponseEntity<TimeCreateResponse> createTime(
            @RequestBody TimeCreateRequest request
    ){
        ReservationTime reservationTime = new ReservationTime(request.startAt());
        UpdatingDAO dao = new UpdatingDAO(jdbcTemplate);
        Long id = dao.insertWithKeyHolder(reservationTime);
        return ResponseEntity.ok(new TimeCreateResponse(id,reservationTime.getStartAt()));
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> readAll() {
        QueryingDAO dao = new QueryingDAO(jdbcTemplate);
        return ResponseEntity.ok(dao.findAllTimes());

    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(
            @PathVariable("id") Long id
    ) {
        UpdatingDAO dao = new UpdatingDAO(jdbcTemplate);
        dao.deleteTime(Long.valueOf(id));
        return  ResponseEntity.ok().build();
    }
}
