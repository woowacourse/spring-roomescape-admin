package roomescape.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import roomescape.dao.QueryingDAO;
import roomescape.dao.UpdatingDAO;
import roomescape.domain.ReservationTime;
import roomescape.dto.TimeCreateRequest;
import roomescape.dto.TimeCreateResponse;
import roomescape.service.ReservationService;

@RestController
public class TimeController {
    private final ReservationService reservationService;

    public TimeController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @PostMapping("/times")
    public ResponseEntity<TimeCreateResponse> createTime(
            @RequestBody TimeCreateRequest request
    ){
        UpdatingDAO dao = reservationService.getUpdatingDAO();
        Long id = dao.insertWithKeyHolder(request.startAt());
        return ResponseEntity.ok(new TimeCreateResponse(id,request.startAt()));
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> readAll() {
        QueryingDAO dao = reservationService.getQueryingDAO();
        return ResponseEntity.ok(dao.findAllTimes());

    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(
            @PathVariable("id") Long id
    ) {
        UpdatingDAO dao = reservationService.getUpdatingDAO();
        dao.deleteTime(Long.valueOf(id));
        return  ResponseEntity.ok().build();
    }
}
