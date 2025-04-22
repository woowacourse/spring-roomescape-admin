package roomescape.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.service.ReservationService;

@RestController
@RequestMapping(value = "/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping()
    public List<ReservationResponse> findAll() {
        return reservationService.findAll();
    }

    @PostMapping()
    public int create(@Valid @RequestBody ReservationCreateRequest reservationCreateRequest) {
//        return reservationService.
        return 1;
    }

    //    @DeleteMapping("/{id}")
//    public int delete(@PathVariable Long id) {
//    }
}
