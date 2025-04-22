package roomescape.time.web;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
}
