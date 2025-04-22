package roomescape.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.QueryingDAO;
import roomescape.dao.UpdatingDAO;
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
}
