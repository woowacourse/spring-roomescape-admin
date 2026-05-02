package roomescape.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import roomescape.dao.QueryingDAO;
import roomescape.dao.UpdatingDAO;
import roomescape.domain.ReservationTime;
import roomescape.dto.TimeCreateRequest;
import roomescape.dto.TimeCreateResponse;
import roomescape.service.ReservationService;

@RestController
@RequestMapping("/times")
public class TimeController {
    private final ReservationService reservationService;

    public TimeController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @PostMapping
    public TimeCreateResponse createTime(
            @RequestBody TimeCreateRequest request
    ){
        UpdatingDAO dao = reservationService.getUpdatingDAO();
        Long id = dao.insertWithKeyHolder(request.startAt());
        return new TimeCreateResponse(id,request.startAt());
    }

    @GetMapping
    public List<ReservationTime> readAll() {
        QueryingDAO dao = reservationService.getQueryingDAO();
        return dao.findAllTimes();

    }

    @DeleteMapping("/{id}")
    public void deleteTime(
            @PathVariable("id") Long id
    ) {
        UpdatingDAO dao = reservationService.getUpdatingDAO();
        dao.deleteTime(Long.valueOf(id));
    }
}
