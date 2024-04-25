package roomescape.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import roomescape.dao.ReservationTimeDAO;
import roomescape.dto.TimeCreateRequestDto;
import roomescape.model.ReservationTime;

@RestController
public class TimeController {

    private final ReservationTimeDAO reservationTimeDAO;

    public TimeController(ReservationTimeDAO reservationTimeDAO) {
        this.reservationTimeDAO = reservationTimeDAO;
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> times() {
        return ResponseEntity.ok(reservationTimeDAO.findAllTimes());
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> create(@RequestBody TimeCreateRequestDto timeCreateRequestDto) {
        ReservationTime reservationTime = reservationTimeDAO.insert(timeCreateRequestDto);
        return ResponseEntity.created(URI.create("/times/" + reservationTime.getId())).body(reservationTime);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            reservationTimeDAO.delete(id);
        } catch (NullPointerException e) {
            System.out.println("ReservationTime id 가 null 입니다.");
        } catch (Exception e) {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
        }
        return ResponseEntity.noContent().build();
    }
}
